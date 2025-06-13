package ar.edu.itba.hci.wallx.data.network

import ar.edu.itba.hci.wallx.data.model.user.AuthenticationData
import ar.edu.itba.hci.wallx.data.model.user.ChangePasswordData
import ar.edu.itba.hci.wallx.data.model.user.CredentialsData
import ar.edu.itba.hci.wallx.data.model.user.NewUserData
import ar.edu.itba.hci.wallx.data.model.user.UserData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiService {
    @POST("user")
    suspend fun createUser(@Body newUser: NewUserData): UserData

    @GET("user")
    suspend fun getUser(): UserData

    @POST("user/resend-verification")
    suspend fun resendVerification(
        @Query("email") email: String
    )

    @POST("user/verify")
    suspend fun verifyUser(
        @Query("code") code: String,
        @Query("email") email: String
    ): UserData

    @POST("user/login")
    suspend fun loginUser(
        @Body credentials: CredentialsData
    ): AuthenticationData

    @POST("user/reset-password")
    suspend fun resetPassword(
        @Query("email") email: String
    )

    @POST("user/change-password")
    suspend fun changePassword(
        @Body changePasswordData: ChangePasswordData
    )

    @POST("user/logout")
    suspend fun logout()
}