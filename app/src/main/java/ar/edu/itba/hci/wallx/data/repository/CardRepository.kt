package ar.edu.itba.hci.wallx.data.repository

import ar.edu.itba.hci.wallx.data.network.model.card.CardData
import ar.edu.itba.hci.wallx.data.network.model.card.NewCardData
import ar.edu.itba.hci.wallx.data.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class CardRepository {

    private val _cards = MutableStateFlow<List<CardData>>(emptyList())
    val cards: StateFlow<List<CardData>> = _cards.asStateFlow()

    suspend fun refreshCards() {
        val result = withContext(Dispatchers.IO) {
            RetrofitInstance.cardApi.getCards()
        }
        _cards.value = result
    }

    suspend fun addCard(newCard: NewCardData): CardData {
        val card = withContext(Dispatchers.IO) {
            RetrofitInstance.cardApi.addCard(newCard)
        }
        refreshCards()
        return card
    }

    suspend fun getCard(id: Int): CardData {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.cardApi.getCard(id)
        }
    }

    suspend fun deleteCard(id: Int) {
        withContext(Dispatchers.IO) {
            RetrofitInstance.cardApi.deleteCard(id)
        }
        refreshCards()
    }
}