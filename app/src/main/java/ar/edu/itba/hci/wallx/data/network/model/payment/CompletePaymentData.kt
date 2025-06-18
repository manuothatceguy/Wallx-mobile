package ar.edu.itba.hci.wallx.data.network.model.payment

import ar.edu.itba.hci.wallx.data.model.Payment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CompletePaymentData(

    @SerialName("paging") var paging: PagingData,
    @SerialName("results") var results: List<PaymentData>

) {
    fun asModel() : List<Payment> {
        return results.map { it.asModel() }.toList()
    }
}