package ar.edu.itba.hci.wallx.data.network.api

import ar.edu.itba.hci.wallx.data.network.model.card.CardData
import ar.edu.itba.hci.wallx.data.network.model.card.NewCardData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CardApiService {

    @GET("/card/")
    suspend fun getCards(): Response<Array<CardData>>

    @POST("/card/")
    suspend fun addCard(
        @Body newCard: NewCardData
    ): Response<CardData>

    @GET("/card/{id}")
    suspend fun getCard(
        @Path("id") id: Int
    ): Response<CardData>

    @DELETE("/card/{id}")
    suspend fun deleteCard(
        @Path("id") id: Int
    ) : Response<Unit>
}
