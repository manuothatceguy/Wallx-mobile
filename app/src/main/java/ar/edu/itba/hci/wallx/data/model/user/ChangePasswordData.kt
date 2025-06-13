package ar.edu.itba.hci.wallx.data.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ChangePasswordData(

    @SerialName("code") var code: String? = null,
    @SerialName("password") var password: String? = null

)