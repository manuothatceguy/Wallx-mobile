package ar.edu.itba.hci.wallx.data.network.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AuthenticationData(

    @SerialName("token") var token: String? = null

)