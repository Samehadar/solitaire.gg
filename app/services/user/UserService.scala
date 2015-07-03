package services.user

import java.util.UUID

import com.github.mauricio.async.db.Connection
import com.mohiva.play.silhouette.api.AuthInfo
import com.mohiva.play.silhouette.impl.providers.CommonSocialProfile
import models.database.queries.auth._
import models.user.User
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import services.database.Database
import services.history.{ RequestHistoryService, GameHistoryService }
import utils.Logging
import utils.cache.CacheService

import scala.concurrent.Future

object UserService extends Logging {
  def create[A <: AuthInfo](currentUser: User, profile: CommonSocialProfile): Future[User] = {
    log.info(s"Saving profile [$profile].")
    UserSearchService.retrieve(profile.loginInfo).flatMap {
      case Some(existingUser) =>
        if (existingUser.id == currentUser.id) {
          val u = existingUser.copy(
            profiles = existingUser.profiles.filterNot(_.providerID == profile.loginInfo.providerID) :+ profile.loginInfo
          )
          save(u, update = true)
        } else {
          Future.successful(existingUser)
        }
      case None => // Link to currentUser
        Database.execute(ProfileQueries.insert(profile)).flatMap { x =>
          val u = currentUser.copy(
            profiles = currentUser.profiles.filterNot(_.providerID == profile.loginInfo.providerID) :+ profile.loginInfo
          )
          save(u, update = true)
        }
    }
  }

  def save(user: User, update: Boolean = false): Future[User] = {
    val statement = if (update) {
      log.info(s"Updating user [$user].")
      UserQueries.UpdateUser(user)
    } else {
      log.info(s"Creating new user [$user].")
      UserQueries.insert(user)
    }
    Database.execute(statement).map { i =>
      CacheService.cacheUser(user)
      user
    }
  }

  def remove(userId: UUID) = {
    val start = System.currentTimeMillis
    Database.transaction { conn =>
      for {
        games <- GameHistoryService.removeGameHistoriesByUser(userId, Some(conn))
        requests <- RequestHistoryService.removeRequestsByUser(userId, Some(conn))
        profiles <- removeProfiles(userId, Some(conn)).map(_.size)
        users <- Database.execute(UserQueries.removeById(Seq(userId)), Some(conn))
      } yield {
        CacheService.removeUser(userId)
        val cardCount = games.map(_._2._2).sum
        val moveCount = games.map(_._2._3).sum
        Map(
          "users" -> users,
          "profiles" -> profiles,
          "requests" -> requests,
          "games" -> games.size,
          "cards" -> cardCount,
          "moves" -> moveCount,
          "timing" -> (System.currentTimeMillis - start).toInt
        )
      }
    }
  }

  private[this] def removeProfiles(userId: UUID, conn: Option[Connection]) = Database.query(ProfileQueries.FindProfilesByUser(userId)).flatMap { profiles =>
    Future.sequence(profiles.map { profile =>
      (profile.loginInfo.providerID match {
        case "credentials" => Database.execute(PasswordInfoQueries.removeById(Seq(profile.loginInfo.providerID, profile.loginInfo.providerKey)), conn)
        case "facebook" => Database.execute(OAuth2InfoQueries.removeById(Seq(profile.loginInfo.providerID, profile.loginInfo.providerKey)), conn)
        case "google" => Database.execute(OAuth2InfoQueries.removeById(Seq(profile.loginInfo.providerID, profile.loginInfo.providerKey)), conn)
        case "twitter" => Database.execute(OAuth1InfoQueries.removeById(Seq(profile.loginInfo.providerID, profile.loginInfo.providerKey)), conn)
        case p => throw new IllegalArgumentException(s"Unknown provider [$p].")
      }).flatMap { infoCount =>
        Database.execute(ProfileQueries.remove(Seq(profile.loginInfo.providerID, profile.loginInfo.providerKey)), conn).map { i =>
          profile
        }
      }
    })
  }
}
