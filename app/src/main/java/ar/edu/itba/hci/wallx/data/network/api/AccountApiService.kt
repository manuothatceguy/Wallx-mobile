package ar.edu.itba.hci.wallx.data.network.api

import ar.edu.itba.hci.wallx.data.network.model.account.AccountData
import ar.edu.itba.hci.wallx.data.network.model.account.AccountUserData
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import retrofit2.Response

interface AccountApiService {

    @GET("/account/")
    suspend fun getAccount(): Response<AccountData>

    @POST("/account/recharge")
    suspend fun rechargeAccount(
        @Query("amount") amount: Double
    ): Response<AccountData>

    @PUT("/account/update-alias")
    suspend fun updateAlias(
        @Query("alias") alias: String
    ): Response<AccountData>

    @GET("/account/verify-cvu")
    suspend fun verifyCvu(
        @Query("cvu") cvu: String
    ): Response<AccountUserData>

    @GET("/account/verify-alias")
    suspend fun verifyAlias(
        @Query("alias") alias: String
    ): Response<AccountUserData>
}
