// Generated rules for Scalataire.
package models.game.rules.generated

import models.game._
import models.game.rules._

/**
 * Original Settings:
 *   Foundation initial cards (F0d): 0 (None)
 *   Enable stock (Sn): 0 (No stock)
 *   Tableau initial cards (T0d): -3 (Fill rows with rest of deck)
 *   Tableau piles (T0n): 10
 *   Tableau rank match rule for building (T0r): 0x0080|0x0020
 *   Tableau suit match rule for building (T0s): 1 (In same suit)
 *   Tableau rank match rule for moving stacks (T0tr): 0x0080|0x0020
 *   Tableau suit match rule for moving stacks (T0ts): 1 (In same suit)
 *   Tableau wraps from king to ace (T0w): true
 *   Number of waste piles (W0n): 0
 *   Similar to (like): chessboard
 *   Low card (lowpip): -2 (?)
 *   Related games (related): lasker, castlesend
 *   Enable super moves, whatever those are (supermoves): 0
 */
object Lasker extends GameRules(
  id = "lasker",
  title = "Lasker",
  like = Some("chessboard"),
  related = Seq("lasker", "castlesend"),
  links = Seq(Link("Pretty Good Solitaire", "www.goodsol.com/pgshelp/lasker.htm")),
  description = "A version of ^chessboard^ where sequences may be moved.",
  deckOptions = DeckOptions(
    lowRank = Rank.Unknown
  ),
  foundations = Seq(
    FoundationRules(
      numPiles = 4,
      wrapFromKingToAce = true,
      autoMoveCards = true
    )
  ),
  tableaus = Seq(
    TableauRules(
      numPiles = 10,
      initialCards = InitialCards.RestOfDeck,
      cardsFaceDown = TableauFaceDownCards.Count(0),
      suitMatchRuleForBuilding = SuitMatchRule.SameSuit,
      rankMatchRuleForBuilding = RankMatchRule.UpOrDown,
      wrapFromKingToAce = true,
      suitMatchRuleForMovingStacks = SuitMatchRule.SameSuit,
      rankMatchRuleForMovingStacks = RankMatchRule.UpOrDown,
      emptyFilledWith = FillEmptyWith.Aces
    )
  ),
  complete = false
)
