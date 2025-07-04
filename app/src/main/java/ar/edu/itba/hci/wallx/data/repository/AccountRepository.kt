package ar.edu.itba.hci.wallx.data.repository

import ar.edu.itba.hci.wallx.data.model.Account
import ar.edu.itba.hci.wallx.data.network.AccountRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class AccountRepository(
    private val remoteDataSource: AccountRemoteDataSource
) {
    val accountDetailStream: Flow<Account> =
        remoteDataSource.accountStream
            .map { it.asModel() }

    suspend fun recharge(amount: Double) {
        remoteDataSource.rechargeAccount(amount)
    }

    suspend fun updateAlias(alias: String) {
        remoteDataSource.updateAlias(alias)
    }

    suspend fun verifyCvu(cvu: String) {
        remoteDataSource.verifyCvu(cvu)
    }

    suspend fun verifyAlias(alias: String) {
        remoteDataSource.verifyAlias(alias)
    }
}