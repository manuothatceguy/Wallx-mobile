package ar.edu.itba.hci.wallx.data.repository

import ar.edu.itba.hci.wallx.data.model.CompleteUser
import ar.edu.itba.hci.wallx.data.network.UserRemoteDataSource
import ar.edu.itba.hci.wallx.data.network.model.user.UserData

class UserRepository(
    private val remoteDataSource: UserRemoteDataSource
) {
    private var user : CompleteUser? = null

    suspend fun login(username: String, password: String) {
        remoteDataSource.login(username, password)
    }

    suspend fun logout() {
        remoteDataSource.logout()
    }

    suspend fun register(firstName : String, lastName : String, birthDate : String, email : String, password : String) {
        remoteDataSource.register(firstName, lastName, birthDate, email, password)
    }

    suspend fun getUser() : CompleteUser {
        return remoteDataSource.getUser().asModel()

    }

    suspend fun verifyUser(code : String, email : String) {
        remoteDataSource.verifyUser(code, email)
    }

    suspend fun resendVerification(email : String) {
        remoteDataSource.resendVerification(email)
    }

    suspend fun resetPassword(email : String) {
        remoteDataSource.resetPassword(email)
    }

    suspend fun changePassword(oldPassword : String, newPassword : String) {
        remoteDataSource.changePassword(oldPassword, newPassword)
    }


}