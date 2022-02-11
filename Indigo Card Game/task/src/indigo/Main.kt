package indigo

import indigo.cards.*
import kotlin.system.exitProcess

fun main() {
    println("Indigo Card Game")
    val deck = Card.randomPermutationOfAllCards()
    val table = UpsideDeck()
    table.addAll(deck.popN(4))
    val participants = Group(
        Participant("Player"),
        Participant("Computer", isComputer = true)
    )
    if (!askPlayFirst()) participants.firstParticipant = participants.get(1)
    participants.current = participants.firstParticipant
    participants.dealCards(6, deck)

    println("Initial cards on the table: $table")
    println()
    printTableState(table)

    while (deck.size > 0 || participants.doParticipantsStillHaveCards) {

        if (!participants.doParticipantsStillHaveCards) participants.dealCards(6, deck)

        val chosenCard: Card

        if (participants.current.isComputer) {
            chosenCard = chooseComputerCard(participants.current.hand, table)
            println("${participants.current.name} plays $chosenCard")
        } else {
            println("Cards in hand: ${participants.current.hand.toOrderedString()}")
            val input = ask("Choose a card to play (1-${participants.current.hand.size}):",
                { isInputExitOrCorrectNumber(it, participants.current.hand.size) })
            if (input == "exit") {
                println("Game Over")
                exitProcess(0)
            } else {
                chosenCard = participants.current.hand.remove(input.toInt() - 1)
            }
        }

        if ((table.size > 0) && chosenCard.matches(table.topMostCard)) {
            table.add(chosenCard)
            println("${participants.current.name} wins cards")
            participants.lastWinner = participants.current
            participants.current.winTable(table)
            printScoreAndCards(participants)
        } else {
            table.add(chosenCard)
        }

        println()

        participants.nextParticipant()
        printTableState(table)
    }

    if (!table.isEmpty) {
        participants.lastWinner.winTable(table)
    }

    printScoreAndCards(participants, isEndOfTheGame = true)
    println("Game Over")

}

fun askPlayFirst() = ask("Play first?\n", { it in listOf("yes", "no") }) == "yes"

fun isInputExitOrCorrectNumber(input: String, handSize: Int) = (
        (input.toIntOrNull() != null && input.toInt() !in 1..handSize)
        || (input.toIntOrNull() == null && input != "exit")
    )

fun printScoreAndCards(participants: Group, isEndOfTheGame: Boolean = false) {
    print("Score: ")
    var maxIndex = 0
    if (isEndOfTheGame) {
        for (i in 0 until participants.numberOfParticipants) {
            if (participants.get(i).numberOfWonCards > participants.get(maxIndex).numberOfWonCards)
                maxIndex = i
        }
    }
    for (i in 0 until participants.numberOfParticipants) {
        print("${participants.get(i).name} ${participants.get(i).score + if (isEndOfTheGame && i == maxIndex) 3 else 0}")
        if (i < participants.numberOfParticipants - 1) print(" - ")
    }
    println()
    print("Cards: ")
    for (i in 0 until participants.numberOfParticipants) {
        print("${participants.get(i).name} ${participants.get(i).numberOfWonCards}")
        if (i < participants.numberOfParticipants - 1) print(" - ")
    }
    println()
}

fun printTableState(table: UpsideDeck) {
    if (table.size > 0) {
        println("${table.size} cards on the table, and the top card is ${table.topMostCard}")
    } else {
        println("No cards on the table")
    }
}

fun chooseComputerCard(hand: Hand, table: UpsideDeck): Card {
    if (hand.size == 1) return hand.remove(0)
    val copyOfHand = hand.copyOfCards
    if (table.size == 0) {
        return hand.remove(intelligentlyPickRandomCardIndex(copyOfHand))
    }
    val candidateCards = hand.getCandidateCardsAndIndices(table.topMostCard)
    return hand.remove(
        when (candidateCards.size) {
            0 -> intelligentlyPickRandomCardIndex(copyOfHand)
            1 -> candidateCards[candidateCards.keys.first()]!!
            else -> {
                candidateCards[
                    candidateCards.keys.toList()[
                        intelligentlyPickRandomCardIndex(candidateCards.keys.toList())
                    ]
                ]!!
            }
        }
    )
}

fun intelligentlyPickRandomCardIndex(cards: List<Card>): Int {
    val alreadySeenSuits = mutableListOf<Suit>()
    for (i in cards.indices) {
        if (cards[i].suit in alreadySeenSuits) {
            return i
        } else alreadySeenSuits.add(cards[i].suit)
    }
    val alreadySeenRanks = mutableListOf<Rank>()
    for (i in cards.indices) {
        if (cards[i].rank in alreadySeenRanks) {
            return i
        } else alreadySeenRanks.add(cards[i].rank)
    }
    return 0
}

fun Hand.getCandidateCardsAndIndices(topmostCard: Card): HashMap<Card, Int> {
    val map = HashMap<Card, Int>()
    val copyOfHand = this.copyOfCards
    for (i in 0 until copyOfHand.size) {
        if (copyOfHand[i].matches(topmostCard)) map[copyOfHand[i]] = i
    }
    return map
}
