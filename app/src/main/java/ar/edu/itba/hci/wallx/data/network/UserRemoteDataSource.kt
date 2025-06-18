package ar.edu.itba.hci.wallx.data.network

import ar.edu.itba.hci.wallx.SessionManager
import ar.edu.itba.hci.wallx.data.network.api.UserApiService
import ar.edu.itba.hci.wallx.data.network.model.user.ChangePasswordData
import ar.edu.itba.hci.wallx.data.network.model.user.CredentialsData
import ar.edu.itba.hci.wallx.data.network.model.user.NewUserData
import ar.edu.itba.hci.wallx.data.network.model.user.UserData


class UserRemoteDataSource(
    private val sessionManager: SessionManager,
    private val userApiService: UserApiService
) : RemoteDataSource() {

    suspend fun login(username: String, password: String) {
        val response = handleApiResponse {
            userApiService.loginUser(CredentialsData(username, password))
        }
        sessionManager.saveAuthToken(response.token.toString())
    }

    suspend fun logout() {
        handleApiResponse { userApiService.logout() }
        sessionManager.removeAuthToken()
    }

    suspend fun register(firstName : String, lastName : String, birthDate : String, email : String, password : String) {
        val response = handleApiResponse {
            userApiService.register(NewUserData(firstName, lastName, birthDate, email, password))
        }
    }

    suspend fun getUser() : UserData {
        return handleApiResponse { userApiService.getUser() }
    }

    suspend fun verifyUser(code : String, email : String) {
        handleApiResponse { userApiService.verifyUser(code, email) }
    }

    suspend fun resendVerification(email : String) {
        handleApiResponse { userApiService.resendVerification(email) }
    }

    suspend fun resetPassword(email : String) {
        handleApiResponse { userApiService.resetPassword(email) }
    }

    suspend fun changePassword(oldPassword : String, newPassword : String) {
        handleApiResponse { userApiService.changePassword(ChangePasswordData(oldPassword, newPassword)) }
    }


}