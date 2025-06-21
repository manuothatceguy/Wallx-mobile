package ar.edu.itba.hci.wallx.data.model

import kotlinx.serialization.Serializable


@Serializable
data class Payment(
    var id: Int? = null,
    var description: String? = null,
    var amount: Double? = null,
    var pending: Boolean? = null,
    var uuid: String? = null,
    var method: String? = null,
    var payer: User? = null,
    var receiver: User? = null,
    var card: SimpleCard? = null,
)
