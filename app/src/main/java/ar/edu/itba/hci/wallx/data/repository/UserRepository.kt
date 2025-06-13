package ar.edu.itba.hci.wallx.data.repository

import ar.edu.itba.hci.wallx.data.model.user.AuthenticationData
import ar.edu.itba.hci.wallx.data.model.user.ChangePasswordData
import ar.edu.itba.hci.wallx.data.model.user.CredentialsData
import ar.edu.itba.hci.wallx.data.model.user.NewUserData
import ar.edu.itba.hci.wallx.data.model.user.UserData
import ar.edu.itba.hci.wallx.data.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class UserRepository {

    private val _user = MutableStateFlow<UserData?>(null)
    val user: StateFlow<UserData?> = _user.asStateFlow()

    suspend fun refreshUser() {
        val result = withContext(Dispatchers.IO) {
            RetrofitInstance.userApi.getUser()
        }
        _user.value = result
    }

    suspend fun login(credentials: CredentialsData): AuthenticationData {
        val result = withContext(Dispatchers.IO) {
            RetrofitInstance.userApi.loginUser(credentials)
        }
        refreshUser()
        return result
    }

    suspend fun logout() {
        withContext(Dispatchers.IO) {
            RetrofitInstance.userApi.logout()
        }
        _user.value = null
    }

    suspend fun register(newUser: NewUserData): UserData {
        val result = withContext(Dispatchers.IO) {
            RetrofitInstance.userApi.createUser(newUser)
        }
        _user.value = result
        return result
    }

    suspend fun resendVerification(email: String) =
        withContext(Dispatchers.IO) {
            RetrofitInstance.userApi.resendVerification(email)
        }

    suspend fun verifyUser(code: String, email: String): UserData =
        withContext(Dispatchers.IO) {
            RetrofitInstance.userApi.verifyUser(code, email)
        }

    suspend fun resetPassword(email: String) =
        withContext(Dispatchers.IO) {
            RetrofitInstance.userApi.resetPassword(email)
        }

    suspend fun changePassword(data: ChangePasswordData) =
        withContext(Dispatchers.IO) {
            RetrofitInstance.userApi.changePassword(data)
        }
}