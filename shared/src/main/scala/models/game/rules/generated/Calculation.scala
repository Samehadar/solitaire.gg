// Generated rules for Scalataire.
package models.game.rules.generated

import models.game._
import models.game.rules._

/**
 * Original Settings:
 *   Foundation low rank (F0b): 23 (Ace, Two, Thre...)
 *   Foundation initial cards (F0d): -1
 *   Foundation rank match rule (F0r): 0x2000
 *   Foundation suit match rule (F0s): 5 (Regardless of suit)
 *   Tableau name (T0Nm): Reserve
 *   Tableau initial cards (T0d): 0 (None)
 *   Empty tableau is filled from (T0fo): BIT_STOCK
 *   Tableau piles (T0n): 4
 *   May move to non-empty tableau from (T0o): BIT_STOCK
 *   Tableau rank match rule for building (T0r): 0x1fff
 *   Tableau suit match rule for building (T0s): 5 (Regardless of suit)
 *   Number of waste piles (W0n): 0
 *   Deal cards from stock (dealto): 7 (Manually)
 */
object Calculation extends GameRules(
  id = "calculation",
  title = "Calculation",
  links = Seq(
    Link("Wikipedia", "en.wikipedia.org/wiki/Calculation_(card_game)"),
    Link("Pretty Good Solitaire", "www.goodsol.com/pgshelp/calculation.htm"),
    Link("Solsuite Solitaire", "www.solsuite.com/games/calculation.htm"),
    Link("Xolitaire", "www.escapedivision.com/xolitaire/en/games/calculation.html"),
    Link("AisleRiot", "help.gnome.org/users/aisleriot/stable/Hopscotch.html.en")
  ),
  description = "Basically similar to ^sirtommy^, but much more complex to play because each foundation pile advances by a different increment.",
  stock = Some(
    StockRules(
      dealTo = StockDealTo.Manually,
      maximumDeals = Some(1)
    )
  ),
  foundations = Seq(
    FoundationRules(
      numPiles = 4,
      lowRank = FoundationLowRank.Ascending,
      initialCards = 4,
      suitMatchRule = SuitMatchRule.Any,
      rankMatchRule = RankMatchRule.UpByPileIndex,
      wrapFromKingToAce = true,
      autoMoveCards = true
    )
  ),
  tableaus = Seq(
    TableauRules(
      name = "Reserve",
      numPiles = 4,
      initialCards = InitialCards.Count(0),
      cardsFaceDown = TableauFaceDownCards.Count(0),
      suitMatchRuleForBuilding = SuitMatchRule.Any,
      rankMatchRuleForBuilding = RankMatchRule.Any,
      suitMatchRuleForMovingStacks = SuitMatchRule.None,
      emptyFilledWith = FillEmptyWith.Aces,
      mayMoveToNonEmptyFrom = Seq("Stock"),
      mayMoveToEmptyFrom = Seq("Stock")
    )
  ),
  complete = false
)
