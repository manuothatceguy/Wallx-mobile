package ar.edu.itba.hci.wallx

import android.app.Application
import ar.edu.itba.hci.wallx.data.network.AccountRemoteDataSource
import ar.edu.itba.hci.wallx.data.network.CardRemoteDataSource
import ar.edu.itba.hci.wallx.data.network.PaymentRemoteDataSource
import ar.edu.itba.hci.wallx.data.network.UserRemoteDataSource
import ar.edu.itba.hci.wallx.data.network.api.RetrofitClient
import ar.edu.itba.hci.wallx.data.repository.AccountRepository
import ar.edu.itba.hci.wallx.data.repository.CardRepository
import ar.edu.itba.hci.wallx.data.repository.PaymentRepository
import ar.edu.itba.hci.wallx.data.repository.UserRepository

class WallXApplication : Application() {

    private val userRemoteDataSource: UserRemoteDataSource
        get() = UserRemoteDataSource(sessionManager, RetrofitClient.getUserApiService(this))

    private val accountRemoteDataSource: AccountRemoteDataSource
        get() = AccountRemoteDataSource(RetrofitClient.getAccountApiService(this))

    private val cardRemoteDataSource: CardRemoteDataSource
        get() = CardRemoteDataSource(RetrofitClient.getCardApiService(this))

    private val paymentRemoteDataSource: PaymentRemoteDataSource
        get() = PaymentRemoteDataSource(RetrofitClient.getPaymentApiService(this))

    val sessionManager: SessionManager
        get() = SessionManager(this)

    val userRepository: UserRepository
        get() = UserRepository(userRemoteDataSource)

    val accountRepository: AccountRepository
        get() = AccountRepository(accountRemoteDataSource)

    val cardRepository: CardRepository
        get() = CardRepository(cardRemoteDataSource)

    val paymentRepository: PaymentRepository
        get() = PaymentRepository(paymentRemoteDataSource)
}