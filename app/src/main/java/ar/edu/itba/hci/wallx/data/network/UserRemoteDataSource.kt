package ar.edu.itba.hci.wallx.data.network

import ar.edu.itba.hci.wallx.SessionManager
import ar.edu.itba.hci.wallx.data.network.api.UserApiService
import ar.edu.itba.hci.wallx.data.network.model.user.CredentialsData


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




}