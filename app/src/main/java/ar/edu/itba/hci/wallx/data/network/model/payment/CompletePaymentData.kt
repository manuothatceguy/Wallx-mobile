package ar.edu.itba.hci.wallx.data.network.model.payment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CompletePaymentData(

    @SerialName("paging") var paging: PagingData,
    @SerialName("results") var results: Array<PaymentData>

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CompletePaymentData

        if (paging != other.paging) return false
        if (!results.contentEquals(other.results)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = paging.hashCode()
        result = 31 * result + results.contentHashCode()
        return result
    }
}