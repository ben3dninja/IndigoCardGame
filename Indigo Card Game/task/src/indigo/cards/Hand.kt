package indigo.cards

class Hand : Deck() {

    val copyOfCards: MutableList<Card>
        get() = cards.toMutableList()

    fun get(index: Int) = cards[index]

    fun set(index: Int, card: Card) {
        cards[index] = card
    }

    fun remove(index: Int) = cards.removeAt(index)

    fun toOrderedString(): String {
        var string = ""
        for (i in cards.indices) {
            string += "${i + 1})${cards[i]} "
        }
        string = string.substring(0 until string.lastIndex)
        return string
    }
}