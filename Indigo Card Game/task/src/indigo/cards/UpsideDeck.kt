package indigo.cards

class UpsideDeck : Deck() {
    val topMostCard: Card
        get() = cards.last()
}