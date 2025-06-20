package ar.edu.itba.hci.wallx.data.network.api

import ar.edu.itba.hci.wallx.data.network.model.payment.CompletePaymentData
import ar.edu.itba.hci.wallx.data.network.model.payment.NewPaymentData
import ar.edu.itba.hci.wallx.data.network.model.payment.PaymentData
import ar.edu.itba.hci.wallx.data.network.model.payment.PendingPaymentData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PaymentApiService {

    @POST("payment/pull")
    suspend fun pullPayment(
        @Body newPayment: NewPaymentData
    ): Response<PendingPaymentData>

    @PUT("payment/push")
    suspend fun pushPayment(
        @Query("uuid") uuid: String,
        @Query("cardId") cardId: Int?
    ): Response<PendingPaymentData>

    @POST("payment/transfer-email")
    suspend fun transferEmail(
        @Query("email") email: String,
        @Query("cardId") cardId: Int?,
        @Body newPayment: NewPaymentData
    ): Response<PendingPaymentData>

    @POST("payment/transfer-cvu")
    suspend fun transferCvu(
        @Query("cvu") cvu: String,
        @Query("cardId") cardId: Int?,
        @Body newPayment: NewPaymentData
    ): Response<PendingPaymentData>

    @POST("payment/transfer-alias")
    suspend fun transferAlias(
        @Query("alias") alias: String,
        @Query("cardId") cardId: Int?,
        @Body newPayment: NewPaymentData
    ): Response<PendingPaymentData>

    @GET("payment")
    suspend fun getAllPayments(
        @Query("page") page: Int? = null,
        @Query("direction") direction: String? = null,
        @Query("pending") pending: Boolean? = null,
        @Query("method") method: String? = null,
        @Query("range") range: String? = null,
        @Query("role") role: String? = null,
        @Query("cardId") cardId: Int? = null
    ): Response<CompletePaymentData>

    @GET("payment/{id}")
    suspend fun getPaymentById(
        @Query("id") id: Int
    ): Response<PaymentData>
}
