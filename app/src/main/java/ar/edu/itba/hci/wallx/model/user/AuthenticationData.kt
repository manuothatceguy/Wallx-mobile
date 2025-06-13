package ar.edu.itba.hci.wallx.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AuthenticationData(

    @SerialName("token") var token: String? = null

)