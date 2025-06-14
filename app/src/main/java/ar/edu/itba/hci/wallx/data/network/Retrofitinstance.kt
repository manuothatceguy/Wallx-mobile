package ar.edu.itba.hci.wallx.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8080/api"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    interface TokenProvider {
        fun getToken(): String?
    }

    // TokenProvider
    object SimpleTokenProvider : TokenProvider {
        private var token: String? = null
        override fun getToken() = token
    }

    private val authInterceptor = Interceptor { chain ->
        val request = chain.request()
        val token = SimpleTokenProvider.getToken()

        val newRequest = if (token != null) {
            request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            request
        }
        chain.proceed(newRequest)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val accountApi: AccountApiService by lazy {
        retrofit.create(AccountApiService::class.java)
    }

    val cardApi: CardApiService by lazy {
        retrofit.create(CardApiService::class.java)
    }

    val paymentApi: PaymentApiService by lazy {
        retrofit.create(PaymentApiService::class.java)
    }

    val userApi: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
}
