package ar.edu.itba.hci.wallx.model.payment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SimpleCardData(

    @SerialName("id") var id: Int? = null,
    @SerialName("number") var number: String? = null

)