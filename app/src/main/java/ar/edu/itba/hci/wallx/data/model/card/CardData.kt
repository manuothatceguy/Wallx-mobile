package ar.edu.itba.hci.wallx.data.model.card

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CardData(

    @SerialName("id") var id: Int? = null,
    @SerialName("type") var type: String? = null,
    @SerialName("number") var number: String? = null,
    @SerialName("expirationDate") var expirationDate: String? = null,
    @SerialName("fullName") var fullName: String? = null,
    @SerialName("metadata") var metadata: Metadata? = Metadata()

)