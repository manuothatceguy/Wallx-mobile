package ar.edu.itba.hci.wallx.data.network

import ar.edu.itba.hci.wallx.data.model.card.Card
import ar.edu.itba.hci.wallx.data.network.api.CardApiService

class CardRemoteDataSource(
    private val cardApiService: CardApiService
) : RemoteDataSource() {
    suspend fun getCards(): List<Card> {
        return handleApiResponse { cardApiService.getCards() }
    }
}