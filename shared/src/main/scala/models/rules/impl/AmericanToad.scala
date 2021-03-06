package models.rules.impl

import models.card.Rank
import models.pile.set.PileSet
import models.rules._

object AmericanToad extends GameRules(
  id = "americantoad",
  completed = false,
  title = "American Toad",
  links = Seq(
    Link("Solitaire Central", "www.solitairecentral.com/rules/AmericanToad.html"),
    Link("Pretty Good Solitaire", "www.goodsol.com/pgshelp/american_toad.htm"),
    Link("Xolitaire", "www.escapedivision.com/xolitaire/en/games/american_toad.html")
  ),
  layout = "swf|r|t",
  deckOptions = DeckOptions(numDecks = 2, lowRank = Rank.Unknown),
  stock = Some(StockRules(maximumDeals = Some(2))),
  waste = Some(WasteRules()),
  foundations = IndexedSeq(
    FoundationRules(
      numPiles = 8,
      initialCards = 1,
      autoMoveCards = true
    )
  ),
  tableaus = IndexedSeq(
    TableauRules(
      numPiles = 8,
      initialCards = InitialCards.Count(1),
      cardsFaceDown = TableauFaceDownCards.Count(0),
      autoFillEmptyFrom = TableauAutoFillEmptyFrom.Reserve,
      mayMoveToEmptyFrom = PileSet.Behavior.allButReserve
    )
  ),
  reserves = Some(ReserveRules(initialCards = 20, cardsFaceDown = -1))
)
