package ar.edu.itba.hci.wallx.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.itba.hci.wallx.data.model.account.AccountData
import ar.edu.itba.hci.wallx.data.model.account.AccountUserData
import ar.edu.itba.hci.wallx.data.repository.AccountRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountViewModel(
    private val accountRepository: AccountRepository
) : ViewModel() {

    val account: StateFlow<AccountData?> = accountRepository.account

    fun refreshAccount() {
        viewModelScope.launch {
            accountRepository.refreshAccount()
        }
    }

    fun rechargeAccount(amount: Double) {
        viewModelScope.launch {
            accountRepository.rechargeAccount(amount)
        }
    }

    fun updateAlias(alias: String) {
        viewModelScope.launch {
            accountRepository.updateAlias(alias)
        }
    }

    suspend fun verifyCvu(cvu: String): AccountUserData {
        return accountRepository.verifyCvu(cvu)
    }

    suspend fun verifyAlias(alias: String): AccountUserData {
        return accountRepository.verifyAlias(alias)
    }
}