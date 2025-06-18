package ar.edu.itba.hci.wallx.data.network.model.card

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewCardData(
    @SerialName("type") var type: String? = null,
    @SerialName("number") var number: String? = null,
    @SerialName("expirationDate") var expirationDate: String? = null,
    @SerialName("fullName") var fullName: String? = null,
    @SerialName("cvv") var cvv: String? = null,
    @SerialName("metadata") var metadata: Metadata? = Metadata()
)
