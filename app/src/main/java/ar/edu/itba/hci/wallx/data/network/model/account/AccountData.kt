package ar.edu.itba.hci.wallx.data.network.model.account

import ar.edu.itba.hci.wallx.data.model.Account
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AccountData(

    @SerialName("id") var id: Int,
    @SerialName("balance") var balance: Double,
    @SerialName("invested") var invested: Double,
    @SerialName("cvu") var cvu: String,
    @SerialName("alias") var alias: String
) {
    fun asModel(): Account {
        return Account(
            id = id,
            balance = balance,
            invested = invested,
            cvu = cvu,
            alias = alias
        )
    }

}