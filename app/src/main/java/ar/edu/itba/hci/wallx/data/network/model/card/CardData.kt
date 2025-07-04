package ar.edu.itba.hci.wallx.data.network.model.card

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ar.edu.itba.hci.wallx.data.model.Card

@Serializable
data class CardData(

    @SerialName("id") var id: Int,
    @SerialName("type") var type: String,
    @SerialName("number") var number: String,
    @SerialName("expirationDate") var expirationDate: String,
    @SerialName("fullName") var fullName: String
) {
    fun asModel(): Card {
        return Card(
            id = id,
            type = type,
            number = number,
            expirationDate = expirationDate,
            fullName = fullName
        )
    }
}