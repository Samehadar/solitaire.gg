package services.user

import java.util.UUID

import com.github.mauricio.async.db.Connection
import models.database.Statement
import models.queries.user.{ UserQueries, UserStatisticsQueries }
import models.history.GameHistory
import models.user.UserStatistics
import org.joda.time.LocalDateTime
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import services.database.Database
import services.history.GameHistoryService
import utils.DateUtils

import scala.concurrent.Future

object UserStatisticsService {
  def registerGame(game: GameHistory): Future[UserStatistics] = {
    if (!game.isCompleted) {
      throw new IllegalStateException(s"Game [${game.id}] is not completed.")
    }
    if (game.logged.isDefined) {
      throw new IllegalStateException(s"Game [${game.id}] is already logged.")
    }

    val update = new Statement {
      override def sql = UserStatisticsQueries.updateSql(game.isWin)
      override def values = Seq[Any](
        game.duration, game.moves, game.undos, game.redos,
        game.completed.getOrElse(throw new IllegalStateException()),
        game.player
      )
    }

    Database.execute(update).flatMap {
      case affected if affected == 1 => Future.successful(Unit)
      case _ => UserStatisticsService.getStatistics(game.player).flatMap { stats =>
        registerGame(game)
      }
    }.flatMap { _ =>
      GameHistoryService.setLogged(game.id, new LocalDateTime()).flatMap { x =>
        getStatistics(game.player)
      }
    }
  }

  def getStatistics(user: UUID) = Database.query(UserStatisticsQueries.getById(user)).flatMap {
    case Some(stats) => Future.successful(stats)
    case None => Database.query(UserQueries.GetCreatedDate(user)).flatMap {
      case Some(joined) =>
        val stats = UserStatistics(user, DateUtils.toMillis(joined), UserStatistics.Games.empty)
        Database.execute(UserStatisticsQueries.insert(stats)).map { _ =>
          stats
        }
      case None => throw new IllegalStateException()
    }
  }

  def removeStatisticsForUser(userId: UUID, conn: Option[Connection]) = {
    Database.execute(UserStatisticsQueries.removeById(userId), conn)
  }
}
