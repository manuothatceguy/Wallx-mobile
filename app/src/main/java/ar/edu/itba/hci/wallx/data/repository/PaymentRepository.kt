package ar.edu.itba.hci.wallx.data.repository

import ar.edu.itba.hci.wallx.data.model.payment.NewPaymentData
import ar.edu.itba.hci.wallx.data.model.payment.PaymentData
import ar.edu.itba.hci.wallx.data.model.payment.PendingPaymentData
import ar.edu.itba.hci.wallx.data.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class PaymentRepository {

    private val _payments = MutableStateFlow<List<PaymentData>>(emptyList())
    val payments: StateFlow<List<PaymentData>> = _payments.asStateFlow()

    suspend fun refreshPayments(
        page: Int? = null,
        direction: String? = null,
        pending: Boolean? = null,
        method: String? = null,
        range: String? = null,
        role: String? = null,
        cardId: Int? = null
    ) {
        val result = withContext(Dispatchers.IO) {
            RetrofitInstance.paymentApi.getAllPayments(
                page,
                direction,
                pending,
                method,
                range,
                role,
                cardId
            )
        }
        _payments.value = result.results // Asegúrate que el campo es el correcto
    }

    suspend fun getPaymentById(id: Int): PaymentData {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.paymentApi.getPaymentById(id)
        }
    }

    suspend fun pullPayment(newPayment: NewPaymentData): PendingPaymentData {
        val response = withContext(Dispatchers.IO) {
            RetrofitInstance.paymentApi.pullPayment(newPayment)
        }
        refreshPayments()
        return response
    }

    suspend fun pushPayment(uuid: String, cardId: Int?): PendingPaymentData {
        val response = withContext(Dispatchers.IO) {
            RetrofitInstance.paymentApi.pushPayment(uuid, cardId)
        }
        refreshPayments()
        return response
    }

    suspend fun transferEmail(
        email: String,
        cardId: Int?,
        newPayment: NewPaymentData
    ): PendingPaymentData {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.paymentApi.transferEmail(email, cardId, newPayment)
        }
    }

    suspend fun transferCvu(
        cvu: String,
        cardId: Int?,
        newPayment: NewPaymentData
    ): PendingPaymentData {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.paymentApi.transferCvu(cvu, cardId, newPayment)
        }
    }

    suspend fun transferAlias(
        alias: String,
        cardId: Int?,
        newPayment: NewPaymentData
    ): PendingPaymentData {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.paymentApi.transferAlias(alias, cardId, newPayment)
        }
    }
}