package ar.edu.itba.hci.wallx.data.network.model.user

import ar.edu.itba.hci.wallx.data.model.CompleteUser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale


@Serializable
data class UserData(

    @SerialName("id") var id: Int? = null,
    @SerialName("firstName") var firstName: String? = null,
    @SerialName("lastName") var lastName: String? = null,
    @SerialName("birthdate") var birthdate: String? = null,
    @SerialName("email") var email: String? = null,
    @SerialName("metadata") var metadata: Metadata? = Metadata()
) {
    fun asModel(): CompleteUser {
        return CompleteUser(
            id = id,
            firstName = firstName ?: "",
            lastName = lastName ?: "",
            email = email ?: "",
            birthDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT)).parse(birthdate!!)
        )
    }
}