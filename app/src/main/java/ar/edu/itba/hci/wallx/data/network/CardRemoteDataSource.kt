package ar.edu.itba.hci.wallx.data.network

import ar.edu.itba.hci.wallx.data.model.card.Card
import ar.edu.itba.hci.wallx.data.network.api.CardApiService
import ar.edu.itba.hci.wallx.data.network.model.card.CardData
import ar.edu.itba.hci.wallx.data.network.model.card.NewCardData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay

class CardRemoteDataSource(
    private val cardApiService: CardApiService
) : RemoteDataSource() {

    val getCardsStream : Flow<Array<CardData>> = flow {
        while (true) {
            val cards = handleApiResponse {
                cardApiService.getCards()
            }
            emit(cards)
            delay(DELAY)
        }
    }

    suspend fun addCard(card: NewCardData) {
        handleApiResponse {
            cardApiService.addCard(card)
        }
    }

    suspend fun deleteCard(id: Int) {
        handleApiResponse {
            cardApiService.deleteCard(id)
        }
    }

    suspend fun getCard(id: Int) : CardData {
        return handleApiResponse {
            cardApiService.getCard(id)
        }
    }


    companion object {
        const val DELAY: Long = 3000
    }

}