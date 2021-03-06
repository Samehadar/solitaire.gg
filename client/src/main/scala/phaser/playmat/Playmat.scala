package phaser.playmat

import com.definitelyscala.phaser.Group
import models.{GameLost, GameWon}
import models.pile.set.PileSet
import phaser.PhaserGame
import phaser.card.CardSprite
import phaser.gameplay.HighlightService
import phaser.pile.PileGroup
import util.NullUtils
import scribe._

import scala.scalajs.js.annotation.ScalaJSDefined

@ScalaJSDefined
class Playmat(val phaser: PhaserGame, val pileSets: IndexedSeq[PileSet], val layoutString: String) extends Group(
  game = phaser, parent = NullUtils.inst, name = "playmat"
) {
  var w: Double = 0.0
  var h: Double = 0.0

  private[this] var cards = Map.empty[Int, (CardSprite, PileGroup, Int)]
  def getCardSprite(id: Int) = cards(id)._1
  def getCards = cards.values.toSeq
  def setCards(c: Map[Int, (CardSprite, PileGroup, Int)]) = cards = c

  val emitter = new PlaymatEmitter(this)

  val resizer = new PlaymatResizer(this)
  resizer.refreshLayout(animate = false)

  val highlightService = new HighlightService(this)

  phaser.add.existing(this)
  phaser.resize(NullUtils.inst)

  private[this] val pileGroups = collection.mutable.HashMap.empty[String, PileGroup]

  def getPileGroups = pileGroups
  def getPileGroup(id: String) = pileGroups.getOrElse(id, throw new IllegalStateException(s"Pile group [$id] not found."))

  def addPileGroup(pileGroup: PileGroup) = {
    pileGroups(pileGroup.id) = pileGroup
    val pileLocation = resizer.layout._3.getOrElse(pileGroup.id, throw new IllegalStateException(s"Missing layout for [${pileGroup.id}]."))
    pileGroup.x = pileLocation._1 * phaser.getSettings.cardSet.w
    pileGroup.y = pileLocation._2 * phaser.getSettings.cardSet.h
  }

  def initialMovesComplete() = pileGroups.filterNot(_._2.behavior == PileSet.Behavior.Pyramid).foreach(_._2.cards.foreach(_.bringToTop()))

  def win(gw: GameWon) = this.logger.info("Win!")
  def lose(gl: GameLost) = this.logger.info("Lose!")
}
