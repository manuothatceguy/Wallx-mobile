package ar.edu.itba.hci.wallx.data.network.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UserData(

    @SerialName("id") var id: Int? = null,
    @SerialName("firstName") var firstName: String? = null,
    @SerialName("lastName") var lastName: String? = null,
    @SerialName("birthdate") var birthdate: String? = null,
    @SerialName("email") var email: String? = null,
    @SerialName("metadata") var metadata: Metadata? = Metadata()

)