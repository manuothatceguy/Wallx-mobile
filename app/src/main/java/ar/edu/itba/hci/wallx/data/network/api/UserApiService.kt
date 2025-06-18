package ar.edu.itba.hci.wallx.data.network.api

import ar.edu.itba.hci.wallx.data.network.model.user.AuthenticationData
import ar.edu.itba.hci.wallx.data.network.model.user.ChangePasswordData
import ar.edu.itba.hci.wallx.data.network.model.user.CredentialsData
import ar.edu.itba.hci.wallx.data.network.model.user.NewUserData
import ar.edu.itba.hci.wallx.data.network.model.user.UserData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiService {
    @POST("user")
    suspend fun register(@Body newUser: NewUserData): Response<UserData>

    @GET("user")
    suspend fun getUser(): Response<UserData>

    @POST("user/resend-verification")
    suspend fun resendVerification(
        @Query("email") email: String
    ): Response<Unit>

    @POST("user/verify")
    suspend fun verifyUser(
        @Query("code") code: String,
        @Query("email") email: String
    ): Response<UserData>

    @POST("user/login")
    suspend fun loginUser(
        @Body credentials: CredentialsData
    ): Response<AuthenticationData>

    @POST("user/reset-password")
    suspend fun resetPassword(
        @Query("email") email: String
    ): Response<Unit>

    @POST("user/change-password")
    suspend fun changePassword(
        @Body changePasswordData: ChangePasswordData
    ): Response<Unit>

    @POST("user/logout")
    suspend fun logout(): Response<Unit>
}