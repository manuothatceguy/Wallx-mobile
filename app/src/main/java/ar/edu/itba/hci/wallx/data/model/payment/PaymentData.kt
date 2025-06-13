package ar.edu.itba.hci.wallx.data.model.payment

import ar.edu.itba.hci.wallx.data.model.account.AccountUserData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PaymentData(

    @SerialName("id") var id: Int? = null,
    @SerialName("description") var description: String? = null,
    @SerialName("amount") var amount: Double? = null,
    @SerialName("pending") var pending: Boolean? = null,
    @SerialName("uuid") var uuid: String? = null,
    @SerialName("method") var method: String? = null,
    @SerialName("payer") var payer: AccountUserData? = null,
    @SerialName("receiver") var receiver: AccountUserData? = null,
    @SerialName("card") var card: SimpleCardData? = null,
    @SerialName("metadata") var metadata: Metadata? = Metadata()

)