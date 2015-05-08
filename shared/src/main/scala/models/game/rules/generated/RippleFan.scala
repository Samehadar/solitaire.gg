// Generated rules for Scalataire.
package models.game.rules.generated

import models.game._
import models.game.rules._

/**
 * Original Settings:
 *   Foundation initial cards (F0d): 0 (None)
 *   Deal order (RDd): 1|0|8
 *   Allowed pick ups/redeals (RDn): -1 (Unlimited)
 *   Pickup order (RDp): 1|0|8
 *   Enable stock (Sn): 0 (No stock)
 *   Tableau initial cards (T0d): 4 (4 cards)
 *   Empty tableau is filled with (T0f): 5 (No card)
 *   Tableau piles (T0n): 13
 *   Tableau suit match rule for building (T0s): 1 (In same suit)
 *   Number of waste piles (W0n): 0
 *   Left mouse interface function (leftfunc): 0x2|0x20
 *   Similar to (like): cruel
 *   Right mouse interface function (rightfunc): 0x0
 */
object RippleFan extends GameRules(
  id = "ripplefan",
  title = "Ripple Fan",
  like = Some("cruel"),
  links = Seq(Link("Pretty Good Solitaire", "www.goodsol.com/pgshelp/ripple_fan.htm")),
  description = "An easier variation of ^cruel^ with one more tableau pile.",
  foundations = Seq(
    FoundationRules(
      numPiles = 4,
      wrapFromKingToAce = true,
      autoMoveCards = true
    )
  ),
  tableaus = Seq(
    TableauRules(
      numPiles = 13,
      initialCards = InitialCards.Count(4),
      cardsFaceDown = TableauFaceDownCards.Count(0),
      suitMatchRuleForBuilding = SuitMatchRule.SameSuit,
      suitMatchRuleForMovingStacks = SuitMatchRule.None
    )
  ),
  complete = false
)
