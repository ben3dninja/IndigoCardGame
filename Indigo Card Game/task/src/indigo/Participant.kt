package indigo

import indigo.cards.Deck
import indigo.cards.Hand
import indigo.cards.UpsideDeck

data class Participant(val name: String, val isComputer: Boolean = false) {

    val hand = Hand()
    private val wonCards = Hand()

    val numberOfWonCards: Int
        get() = wonCards.size

    val score: Int
        get() {
            var temp = 0
            for (card in wonCards.copyOfCards) {
                temp += card.points
            }
            return temp
        }

    fun dealCards(numberOfCards: Int, deck: Deck) {
        hand.addAll(deck.popN(numberOfCards))
    }

    fun winTable(table: UpsideDeck) {
        wonCards.addAll(table.popN(table.size))
    }
}