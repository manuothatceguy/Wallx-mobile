package ar.edu.itba.hci.wallx.data.repository

import ar.edu.itba.hci.wallx.data.network.UserRemoteDataSource
import ar.edu.itba.hci.wallx.data.network.model.user.AuthenticationData
import ar.edu.itba.hci.wallx.data.network.model.user.ChangePasswordData
import ar.edu.itba.hci.wallx.data.network.model.user.CredentialsData
import ar.edu.itba.hci.wallx.data.network.model.user.NewUserData
import ar.edu.itba.hci.wallx.data.network.model.user.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class UserRepository(
    private val remoteDataSource: UserRemoteDataSource
) {
    suspend fun login(username: String, password: String) {
        remoteDataSource.login(username, password)
    }

    suspend fun logout() {
        remoteDataSource.logout()
    }


}