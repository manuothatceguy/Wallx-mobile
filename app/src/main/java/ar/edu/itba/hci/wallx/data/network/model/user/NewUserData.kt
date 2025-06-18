package ar.edu.itba.hci.wallx.data.network.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NewUserData(

    @SerialName("firstName") var firstName: String? = null,
    @SerialName("lastName") var lastName: String? = null,
    @SerialName("birthDate") var birthDate: String? = null,
    @SerialName("email") var email: String? = null,
    @SerialName("password") var password: String? = null,
    @SerialName("metadata") var metadata: Metadata? = Metadata()

)