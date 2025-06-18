package ar.edu.itba.hci.wallx.data.network.model.payment

import ar.edu.itba.hci.wallx.data.model.SimpleCard
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SimpleCardData(

    @SerialName("id") var id: Int,
    @SerialName("number") var number: String

) {
    fun asModel() : SimpleCard {
        return SimpleCard(
            id = id,
            number = number
        )
    }
}