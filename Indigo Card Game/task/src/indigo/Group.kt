package indigo

import indigo.cards.Deck

class Group(vararg participantsArray: Participant) {

    init {
        if (participantsArray.isEmpty()) throw Exception("Group must contain at least one participant")
    }

    private val participants: MutableList<Participant> = participantsArray.toMutableList()

    var firstParticipant = participants.first()
    var current = firstParticipant
    var lastWinner = firstParticipant

    val numberOfParticipants: Int
        get() = participants.size

    val doParticipantsStillHaveCards: Boolean
        get() {
            for (participant in participants) {
                if (!participant.hand.isEmpty) return true
            }
            return false
        }

    fun get(index: Int) = participants[index]

    fun dealCards(numberOfCards: Int, deck: Deck) {
        for (participant in participants) {
            participant.dealCards(numberOfCards, deck)
        }
    }

    fun nextParticipant() {
        current = participants[(participants.indexOf(current) + 1) % numberOfParticipants]
    }
}