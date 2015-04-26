package models.game.rules.custom

import models.game.rules._

object Sandbox extends GameRules(
  id = "sandbox",
  title = "Sandbox",
  description = "A work in progress...",
  cardRemovalMethod = CardRemovalMethod.BuildSequencesOnFoundation,

  foundations = Seq(FoundationRules(
    suitMatchRule = SuitMatchRule.Any,
    rankMatchRule = RankMatchRule.Any
  )),
  stock = Some(StockRules(
    dealTo = StockDealTo.Foundation
  ))
)