package ar.edu.itba.hci.wallx.data.repository

import ar.edu.itba.hci.wallx.data.model.Card
import ar.edu.itba.hci.wallx.data.network.CardRemoteDataSource
import ar.edu.itba.hci.wallx.data.network.model.card.NewCardData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CardRepository(
    private val remoteDataSource: CardRemoteDataSource
) {
    val cardsStream: Flow<List<Card>> =
        remoteDataSource.getCardsStream.map { list ->
            list.map { it.asModel() }.toList<Card>()
        }

    suspend fun addCard(card: NewCardData) {
        remoteDataSource.addCard(card)
    }

    suspend fun deleteCard(id: Int) {
        remoteDataSource.deleteCard(id)
    }

    suspend fun getCard(id: Int) : Card {
        return remoteDataSource.getCard(id).asModel()
    }

}