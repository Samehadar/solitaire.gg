package models

import java.util.UUID

sealed trait RequestMessage

case class MalformedRequest(reason: String, content: String) extends RequestMessage
case class Ping(timestamp: Long) extends RequestMessage
case object GetVersion extends RequestMessage
case class DebugInfo(data: String) extends RequestMessage

case class StartGame(rules: String, seed: Option[Int] = None) extends RequestMessage
case class JoinGame(id: UUID) extends RequestMessage
case class ObserveGame(id: UUID, as: Option[UUID]) extends RequestMessage

trait GameMessage extends RequestMessage

case object GetPossibleMoves extends GameMessage
case class SelectCard(card: UUID, pile: String) extends GameMessage
case class SelectPile(pile: String) extends GameMessage
case class MoveCards(cards: Seq[UUID], src: String, tgt: String) extends GameMessage

case object Undo extends GameMessage
case object Redo extends GameMessage

case object ResignGame extends GameMessage
