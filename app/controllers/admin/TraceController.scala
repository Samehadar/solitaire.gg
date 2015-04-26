package controllers.admin

import java.util.UUID

import akka.pattern.ask
import akka.util.Timeout
import controllers.BaseController
import controllers.BaseController.AuthenticatedAction
import models._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import services.ActorSupervisor

import scala.concurrent.duration._

object TraceController extends BaseController {
  implicit val timeout = Timeout(10.seconds)

  def traceConnection(connectionId: UUID) = AuthenticatedAction.async { implicit request =>
    (ActorSupervisor.instance ask ConnectionTrace(connectionId)).map {
      case tr: TraceResponse => requireAdmin {
        Ok(views.html.admin.trace("Connection", tr))
      }
      case se: ServerError => requireAdmin {
        Ok(se.reason + ": " + se.content)
      }
    }
  }

  def traceClient(connectionId: UUID) = AuthenticatedAction.async { implicit request =>
    (ActorSupervisor.instance ask ClientTrace(connectionId)).map {
      case tr: TraceResponse => requireAdmin {
        Ok(views.html.admin.trace("Client", tr))
      }
      case se: ServerError => requireAdmin {
        Ok(se.reason + ": " + se.content)
      }
    }
  }

  def traceGame(gameId: UUID) = AuthenticatedAction.async { implicit request =>
    (ActorSupervisor.instance ask GameTrace(gameId)).map {
      case tr: TraceResponse => requireAdmin {
        val buttons = Seq("Observe As Admin" -> controllers.admin.routes.AdminController.observeGameAsAdmin(gameId).url)
        Ok(views.html.admin.trace("Game", tr, buttons))
      }
      case se: ServerError => requireAdmin {
        Ok(se.reason + ": " + se.content)
      }
    }
  }
}