package ar.edu.itba.hci.wallx.data.repository

import ar.edu.itba.hci.wallx.data.model.account.AccountData
import ar.edu.itba.hci.wallx.data.model.account.AccountUserData
import ar.edu.itba.hci.wallx.data.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class AccountRepository {

    private val _account = MutableStateFlow<AccountData?>(null)
    val account: StateFlow<AccountData?> = _account.asStateFlow()

    suspend fun refreshAccount() {
        val result = withContext(Dispatchers.IO) {
            RetrofitInstance.accountApi.getAccount()
        }
        _account.value = result
    }

    suspend fun rechargeAccount(amount: Double) {
        val updated = withContext(Dispatchers.IO) {
            RetrofitInstance.accountApi.rechargeAccount(amount)
        }
        _account.value = updated
    }

    suspend fun updateAlias(alias: String) {
        val updated = withContext(Dispatchers.IO) {
            RetrofitInstance.accountApi.updateAlias(alias)
        }
        _account.value = updated
    }

    suspend fun verifyCvu(cvu: String): AccountUserData {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.accountApi.verifyCvu(cvu)
        }
    }

    suspend fun verifyAlias(alias: String): AccountUserData {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.accountApi.verifyAlias(alias)
        }
    }
}