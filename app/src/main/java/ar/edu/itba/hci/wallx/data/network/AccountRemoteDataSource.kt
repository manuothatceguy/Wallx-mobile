package ar.edu.itba.hci.wallx.data.network

import ar.edu.itba.hci.wallx.data.network.api.AccountApiService
import ar.edu.itba.hci.wallx.data.network.model.account.AccountData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AccountRemoteDataSource(
    private val accountApiService: AccountApiService
) : RemoteDataSource() {

    val accountStream: Flow<AccountData> = flow {
        while (true) {
            val walletDetail = handleApiResponse {
                accountApiService.getAccount()
            }
            emit(walletDetail)
            delay(DELAY)
        }
    }

    companion object {
        const val DELAY: Long = 3000
    }
}