package ar.edu.itba.hci.wallx.network

import ar.edu.itba.hci.wallx.model.account.AccountData
import ar.edu.itba.hci.wallx.model.account.AccountUserData
import ar.edu.itba.hci.wallx.model.card.CardData
import ar.edu.itba.hci.wallx.model.card.NewCardData
import ar.edu.itba.hci.wallx.model.payment.CompletePaymentData
import ar.edu.itba.hci.wallx.model.payment.NewPaymentData
import ar.edu.itba.hci.wallx.model.payment.PaymentData
import ar.edu.itba.hci.wallx.model.payment.PendingPaymentData
import ar.edu.itba.hci.wallx.model.user.AuthenticationData
import ar.edu.itba.hci.wallx.model.user.ChangePasswordData
import ar.edu.itba.hci.wallx.model.user.CredentialsData
import ar.edu.itba.hci.wallx.model.user.NewUserData
import ar.edu.itba.hci.wallx.model.user.UserData
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "http://localhost:8080/api"

private val httpLoggingInterceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor)
    .build()

private val json = Json { ignoreUnknownKeys = true }

private val retrofit = (Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build())

object ApiRoutes {
    const val ACCOUNT = "/account"
    const val CARD = "/card"
    const val PAYMENT = "/payment"
    const val USER = "/user"
}

interface AccountApiService {

    @GET("${ApiRoutes.ACCOUNT}/")
    suspend fun getAccount(): AccountData

    @POST("${ApiRoutes.ACCOUNT}/recharge")
    suspend fun rechargeAccount(@Query("amount") amount: Double): AccountData

    @PUT("${ApiRoutes.ACCOUNT}/update-alias")
    suspend fun updateAlias(@Query("alias") alias: String): AccountData

    @GET("${ApiRoutes.ACCOUNT}/verify-cvu")
    suspend fun verifyCvu(@Query("cvu") cvu: String): AccountUserData

    @GET("${ApiRoutes.ACCOUNT}/verify-alias")
    suspend fun verifyAlias(@Query("alias") alias: String): AccountUserData
}

interface CardApiService {

    @GET("${ApiRoutes.CARD}/")
    suspend fun getCards(): List<CardData>

    @POST("${ApiRoutes.CARD}/")
    suspend fun addCard(@Body newCard: NewCardData): CardData

    @GET("${ApiRoutes.CARD}/")
    suspend fun getCard(@Path("id") id: Int): CardData

    @DELETE("${ApiRoutes.CARD}/")
    suspend fun deleteCard(@Path("id") id: Int)

}

interface PaymentApiService {

    @POST("${ApiRoutes.PAYMENT}/pull")
    suspend fun pullPayment(@Body newPayment: NewPaymentData): PendingPaymentData

    @PUT("${ApiRoutes.PAYMENT}/push")
    suspend fun pushPayment(
        @Query("uuid") uuid: String,
        @Query("cardId") cardId: Int?
    ): PendingPaymentData

    @POST("${ApiRoutes.PAYMENT}/transfer-email")
    suspend fun transferEmail(
        @Query("email") email: String,
        @Query("cardId") cardId: Int?,
        @Body newPayment: NewPaymentData
    ): PendingPaymentData

    @POST("${ApiRoutes.PAYMENT}/transfer-cvu")
    suspend fun transferCvu(
        @Query("cvu") cvu: String,
        @Query("cardId") cardId: Int?,
        @Body newPayment: NewPaymentData
    ): PendingPaymentData

    @POST("${ApiRoutes.PAYMENT}/transfer-alias")
    suspend fun transferAlias(
        @Query("alias") alias: String,
        @Query("cardId") cardId: Int?,
        @Body newPayment: NewPaymentData
    ): PendingPaymentData

    @GET("${ApiRoutes.PAYMENT}/payment")
    suspend fun getPayment(
        @Query("page") page: Int?,
        @Query("direction") direction: String?,
        @Query("pending") pending: Boolean?,
        @Query("method") method: String?,
        @Query("range") range: String?,
        @Query("role") role: String?,
        @Query("cardId") cardId: Int?

    ): CompletePaymentData

    @GET("${ApiRoutes.PAYMENT}/payment")
    suspend fun getPayment(
        @Query("id") id: Int
    ): PaymentData

}

interface UserApiService {

    @POST("${ApiRoutes.USER}/")
    suspend fun createUser(@Body newUser: NewUserData): UserData

    @GET("${ApiRoutes.USER}/")
    suspend fun getUser(): UserData

    @POST("${ApiRoutes.USER}/resend-verification")
    suspend fun resendVerification(
        @Query("email") email: String
    )

    @POST("${ApiRoutes.USER}/verify")
    suspend fun verifyUser(
        @Query("code") code: String,
        @Body email: String
    ): UserData

    @POST("${ApiRoutes.USER}/login")
    suspend fun loginUser(
        @Body credentials: CredentialsData
    ): AuthenticationData

    @POST("${ApiRoutes.USER}/reset-password")
    suspend fun resetPassword(
        @Query("email") email: String
    )

    @POST("${ApiRoutes.USER}/change-password")
    suspend fun changePassword(
        @Body changePasswordData: ChangePasswordData
    )

    @POST("${ApiRoutes.USER}/logout")
    suspend fun logout()
}