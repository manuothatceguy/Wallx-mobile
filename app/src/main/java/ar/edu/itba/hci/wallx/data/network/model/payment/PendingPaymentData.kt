package ar.edu.itba.hci.wallx.data.network.model.payment

import ar.edu.itba.hci.wallx.data.network.model.account.AccountUserData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PendingPaymentData(

    @SerialName("id") var id: Int? = null,
    @SerialName("description") var description: String? = null,
    @SerialName("amount") var amount: Double? = null,
    @SerialName("pending") var pending: Boolean? = null,
    @SerialName("uuid") var uuid: String? = null,
    @SerialName("receiver") var receiver: AccountUserData? = null
)