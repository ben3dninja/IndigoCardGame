package indigo.cards

open class Deck {

    protected val cards: MutableList<Card> = mutableListOf()

    val size: Int
        get() = cards.size

    val isEmpty: Boolean
        get() = cards.isEmpty()

    fun add(card: Card) = cards.add(card)

    fun addAll(deck: Deck) {
        for (i in 0 until deck.size) {
            add(deck.pop())
        }
    }

    fun pop() = cards.removeLast()

    fun popN(number: Int): Deck {
        val deck = Deck()
        for (i in 0 until number) {
            deck.add(pop())
        }
        return deck
    }

    fun shuffle() = cards.shuffle()

    override fun toString() = cards.joinToString(" ")
}