package ar.edu.itba.hci.wallx.model.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AccountUserData(

    @SerialName("firstName") var firstName: String? = null,
    @SerialName("lastName") var lastName: String? = null

)