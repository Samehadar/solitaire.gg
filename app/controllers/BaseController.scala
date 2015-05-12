package controllers

import java.util.UUID

import controllers.BaseController.AuthenticatedRequest
import nl.grons.metrics.scala.Timer
import play.api.libs.Crypto
import play.api.mvc._
import services.account.AccountService
import utils.metrics.Instrumented
import utils.{ Config, Logging }

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext

object BaseController extends Instrumented {
  val timers = collection.mutable.HashMap.empty[String, Timer]

  class AuthenticatedRequest[A](val accountId: UUID, val name: String, val role: String, request: Request[A]) extends WrappedRequest[A](request) {
    def isAdmin = role == "admin"
  }

  object AuthenticatedAction extends ActionBuilder[AuthenticatedRequest] {
    override def invokeBlock[A](request: Request[A], block: (AuthenticatedRequest[A]) => Future[Result]): Future[Result] = {
      val timer = timers.getOrElseUpdate(request.path, metrics.timer("request." + request.path.substring(1).replaceAll("\\/", "\\.")))
      timer.time {
        val fallBackAccount = request.cookies.get("solitaire_gg_account").map(c => UUID.fromString(Crypto.decryptAES(c.value)))
        AccountService.getAccount(request.session.data, fallBackAccount).flatMap {
          case (accountId, name, role, newAccount) =>
            val newRequest = new AuthenticatedRequest(accountId, name, role, request)
            if (newAccount) {
              block(newRequest).map { result =>
                result.withSession {
                  request.session + ("account" -> accountId.toString) + ("name" -> name) + ("role" -> role)
                }.withCookies(
                  Cookie("solitaire_gg_account", Crypto.encryptAES(accountId.toString), Some(31556000 /* 1 year */ ), "/", None, secure = false)
                )
              }
            } else {
              block(newRequest)
            }
        }
      }
    }
  }
}

abstract class BaseController extends Controller with Logging {
  def requireAdmin(f: => Result)(implicit request: AuthenticatedRequest[AnyContent]) = {
    if (request.isAdmin) {
      f
    } else {
      Redirect(controllers.routes.HomeController.index())
    }
  }
}
