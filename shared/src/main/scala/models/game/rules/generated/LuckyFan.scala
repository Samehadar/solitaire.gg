// Generated rules for Scalataire.
package models.game.rules.generated

import models.game._
import models.game.rules._

/**
 * Original Settings:
 *   Number of cells (C0n): 2
 *   Auto-move cards to foundation (F0a): 1 (Whenever possible)
 *   Enable stock (Sn): 0 (No stock)
 *   Tableau name (T0Nm): Fan
 *   Tableau initial cards (T0d): -3 (Fill rows with rest of deck)
 *   Empty tableau is filled with (T0f): 1 (Kings only)
 *   Maximum cards per tableau (T0m): 3 (3 cards)
 *   Tableau piles (T0n): 18
 *   Tableau suit match rule for building (T0s): 1 (In same suit)
 *   Number of waste piles (W0n): 0
 *   Similar to (like): freefan
 *   Related games (related): boxfan, freefan, ceilingfan, midnightclover
 */
object LuckyFan extends GameRules(
  id = "luckyfan",
  title = "Lucky Fan",
  like = Some("freefan"),
  related = Seq("boxfan", "freefan", "ceilingfan", "midnightclover"),
  links = Seq(Link("Pretty Good Solitaire", "www.goodsol.com/pgshelp/lucky_fan.htm")),
  description = "A version of ^freefan^ in which no fan may hold more than three cards.",
  foundations = Seq(
    FoundationRules(
      numPiles = 4,
      wrapFromKingToAce = true,
      autoMoveCards = true
    )
  ),
  tableaus = Seq(
    TableauRules(
      name = "Fan",
      numPiles = 18,
      initialCards = InitialCards.RestOfDeck,
      cardsFaceDown = TableauFaceDownCards.Count(0),
      suitMatchRuleForBuilding = SuitMatchRule.SameSuit,
      suitMatchRuleForMovingStacks = SuitMatchRule.None,
      emptyFilledWith = FillEmptyWith.Kings,
      maxCards = 3
    )
  ),
  cells = Some(
    CellRules(
      numPiles = 2
    )
  ),
  complete = false
)
