package ar.edu.itba.hci.wallx.data.network

import ar.edu.itba.hci.wallx.data.network.api.AccountApiService
import ar.edu.itba.hci.wallx.data.network.model.account.AccountData
import ar.edu.itba.hci.wallx.data.network.model.account.AccountUserData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AccountRemoteDataSource(
    private val accountApiService: AccountApiService
) : RemoteDataSource() {

    val accountStream: Flow<AccountData> = flow {
        while (true) {
            val accountDetail = handleApiResponse {
                accountApiService.getAccount()
            }
            emit(accountDetail)
            delay(DELAY)
        }
    }

    suspend fun rechargeAccount(amount: Double) {
        handleApiResponse {
            accountApiService.rechargeAccount(amount)
        }
    }

    suspend fun updateAlias(alias: String) {
        handleApiResponse {
            accountApiService.updateAlias(alias)
        }
    }

    suspend fun verifyCvu(cvu: String) : AccountUserData {
         return handleApiResponse {
            accountApiService.verifyCvu(cvu)
        }
    }

    suspend fun verifyAlias(alias: String) : AccountUserData {
        return handleApiResponse {
            accountApiService.verifyAlias(alias)
        }
    }

    companion object {
        const val DELAY: Long = 3000
    }
}