package ar.edu.itba.hci.wallx.data.network.api

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitClient {
    // No usar localhost o la IP 127.0.0.1 porque es la interfaz de loopback
    // del emulador. La forma de salir del emulador para acceder al localhost
    // de host del mismo es usando la IP 10.0.2.2.
    //private const val BASE_URL = "http://10.0.2.2:8080/api/" LOCAL
    private const val BASE_URL = "https://2484-181-12-200-83.ngrok-free.app/api/"


    @Volatile
    private var instance: Retrofit? = null

    private fun getInstance(context: Context): Retrofit =
        instance ?: synchronized(this) {
            instance ?: buildRetrofit(context).also { instance = it }
        }

    private fun buildRetrofit(context: Context): Retrofit {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val json = Json { ignoreUnknownKeys = true }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
    }

    fun getUserApiService(context: Context): UserApiService {
        return getInstance(context).create(UserApiService::class.java)
    }

    fun getAccountApiService(context: Context): AccountApiService {
        return getInstance(context).create(AccountApiService::class.java)
    }

    fun getCardApiService(context: Context): CardApiService {
        return getInstance(context).create(CardApiService::class.java)
    }

    fun getPaymentApiService(context: Context): PaymentApiService {
        return getInstance(context).create(PaymentApiService::class.java)
    }
}
