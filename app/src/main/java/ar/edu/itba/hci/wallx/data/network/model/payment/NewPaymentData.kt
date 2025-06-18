package ar.edu.itba.hci.wallx.data.network.model.payment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NewPaymentData(

    @SerialName("description") var description: String? = null,
    @SerialName("amount") var amount: Double? = null,
    @SerialName("metadata") var metadata: Metadata? = Metadata()

)