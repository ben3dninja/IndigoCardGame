package indigo.cards

enum class Suit(private val symbol: Char) {
    CLOVERS('♣'),
    DIAMONDS('♦'),
    HEARTS('♥'),
    SPADES('♠');

    override fun toString() = symbol.toString()
}