package indigo.cards

data class Card(val rank: Rank, val suit: Suit) {
    override fun toString() = "$rank$suit"

    val points = if (rank in listOf(Rank.ACE, Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING)) 1 else 0

    companion object {

        fun randomPermutationOfAllCards(): Deck {
            val deck = Deck()
            for (suit in Suit.values()) {
                for (rank in Rank.values()) {
                    deck.add(Card(rank, suit))
                }
            }
            deck.shuffle()
            return deck
        }
    }

    fun matches(other: Card) = rank == other.rank || suit == other.suit
}
