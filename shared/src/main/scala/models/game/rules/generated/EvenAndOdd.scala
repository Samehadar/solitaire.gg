// Generated rules for Scalataire.
package models.game.rules.generated

import models.game._
import models.game.rules._

/**
 * Original Settings:
 *   Auto-move cards to foundation (F0a): 0 (Never)
 *   Maximum cards for foundation (F0m): 7
 *   Number of foundation piles (F0n): 4 (4 stacks)
 *   Foundation rank match rule (F0r): 0x0100
 *   Foundation suit match rule (F0s): 5 (Regardless of suit)
 *   TODO (F0u): 2
 *   Auto-move cards to foundation (F1a): 0 (Never)
 *   Foundation low rank (F1b): 2 (2)
 *   Maximum cards for foundation (F1m): 6
 *   Number of foundation piles (F1n): 4 (4 stacks)
 *   Foundation rank match rule (F1r): 0x0100
 *   Foundation suit match rule (F1s): 5 (Regardless of suit)
 *   TODO (F1u): 2
 *   Foundation Sets (Fn): 2
 *   Reserve initial cards (R0d): 6
 *   Number of reserve piles (R0n): 3
 *   Auto-fill an empty tableau from (T0af): 2|4
 *   Tableau initial cards (T0d): 1 (1 card)
 *   Empty tableau is filled with (T0f): 5 (No card)
 *   Tableau piles (T0n): 9
 *   Tableau suit match rule for building (T0s): 0 (May not build)
 *   Left mouse interface function (leftfunc): 0x2
 *   Similar to (like): boulevard
 *   Maximum deals from stock (maxdeals): 1 (1)
 *   Number of decks (ndecks): 1 (1 deck)
 *   Related games (related): evenandodd
 *   Touch interface function (touchfunc): 0x2
 */
object EvenAndOdd extends GameRules(
  id = "evenandodd",
  title = "Even and Odd",
  like = Some("boulevard"),
  related = Seq("evenandodd"),
  links = Seq(Link("BVS Solitaire Collection", "www.bvssolitaire.com/rules/even-and-odd.htm")),
  description = "A one-deck version of ^boulevard^.",
  stock = Some(
    StockRules(
      maximumDeals = Some(1)
    )
  ),
  waste = Some(WasteRules()),
  foundations = Seq(
    FoundationRules(
      numPiles = 4,
      suitMatchRule = SuitMatchRule.Any,
      rankMatchRule = RankMatchRule.UpBy2,
      wrapFromKingToAce = true,
      maxCards = 7
    ),
    FoundationRules(
      setNumber = 1,
      numPiles = 4,
      lowRank = FoundationLowRank.SpecificRank(Rank.Two),
      suitMatchRule = SuitMatchRule.Any,
      rankMatchRule = RankMatchRule.UpBy2,
      wrapFromKingToAce = true,
      maxCards = 6
    )
  ),
  tableaus = Seq(
    TableauRules(
      numPiles = 9,
      initialCards = InitialCards.Count(1),
      cardsFaceDown = TableauFaceDownCards.Count(0),
      suitMatchRuleForBuilding = SuitMatchRule.None,
      suitMatchRuleForMovingStacks = SuitMatchRule.None,
      autoFillEmptyFrom = TableauAutoFillEmptyFrom.WasteThenStock
    )
  ),
  reserves = Some(
    ReserveRules(
      name = "Reserve",
      numPiles = 3,
      initialCards = 6,
      cardsFaceDown = 100
    )
  ),
  complete = false
)
