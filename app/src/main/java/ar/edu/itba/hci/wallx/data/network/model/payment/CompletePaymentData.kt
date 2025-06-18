package ar.edu.itba.hci.wallx.data.network.model.payment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CompletePaymentData(

    @SerialName("paging") var paging: PagingData? = null,
    @SerialName("results") var results: ArrayList<PaymentData> = arrayListOf()

)