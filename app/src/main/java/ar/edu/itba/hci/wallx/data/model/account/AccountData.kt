package ar.edu.itba.hci.wallx.data.model.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AccountData(

    @SerialName("id") var id: Int? = null,
    @SerialName("balance") var balance: Double? = null,
    @SerialName("invested") var invested: Int? = null,
    @SerialName("cvu") var cvu: String? = null,
    @SerialName("alias") var alias: String? = null

)