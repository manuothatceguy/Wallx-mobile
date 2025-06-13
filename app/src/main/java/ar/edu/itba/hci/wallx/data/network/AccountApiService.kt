package ar.edu.itba.hci.wallx.data.network

import ar.edu.itba.hci.wallx.data.model.account.AccountData
import ar.edu.itba.hci.wallx.data.model.account.AccountUserData
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AccountApiService {

    @GET("/account/")
    suspend fun getAccount(): AccountData

    @POST("/account/recharge")
    suspend fun rechargeAccount(
        @Query("amount") amount: Double
    ): AccountData

    @PUT("/account/update-alias")
    suspend fun updateAlias(
        @Query("alias") alias: String
    ): AccountData

    @GET("/account/verify-cvu")
    suspend fun verifyCvu(
        @Query("cvu") cvu: String
    ): AccountUserData

    @GET("/account/verify-alias")
    suspend fun verifyAlias(
        @Query("alias") alias: String
    ): AccountUserData
}
