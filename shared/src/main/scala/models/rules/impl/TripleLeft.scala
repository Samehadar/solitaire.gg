package models.rules.impl

import models.rules._

object TripleLeft extends GameRules(
  id = "tripleleft",
  completed = true,
  title = "Triple Left",
  like = Some("movingleft"),
  links = Seq(Link("Pretty Good Solitaire", "www.goodsol.com/pgshelp/triple_left.htm")),
  layout = "swf|.:t",
  deckOptions = DeckOptions(numDecks = 3),
  stock = Some(
    StockRules(
      maximumDeals = Some(1)
    )
  ),
  waste = Some(WasteRules()),
  foundations = IndexedSeq(
    FoundationRules(
      numPiles = 12,
      autoMoveCards = true
    )
  ),
  tableaus = IndexedSeq(
    TableauRules(
      numPiles = 12,
      autoFillEmptyFrom = TableauAutoFillEmptyFrom.NextPile,
      emptyFilledWith = FillEmptyWith.HighRank
    )
  )
)
