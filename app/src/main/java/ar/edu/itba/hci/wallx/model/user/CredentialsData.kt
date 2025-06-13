package ar.edu.itba.hci.wallx.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CredentialsData(

    @SerialName("email") var email: String? = null,
    @SerialName("password") var password: String? = null

)