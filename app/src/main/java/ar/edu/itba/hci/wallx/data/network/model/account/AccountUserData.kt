package ar.edu.itba.hci.wallx.data.network.model.account

import ar.edu.itba.hci.wallx.data.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AccountUserData(
    @SerialName("id") var id: Int,
    @SerialName("firstName") var firstName: String,
    @SerialName("lastName") var lastName: String

) {
    fun asModel() : User {
        return User(
            id = id,
            firstName = firstName,
            lastName = lastName
        )
    }
}