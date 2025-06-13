package ar.edu.itba.hci.wallx.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.itba.hci.wallx.data.model.payment.NewPaymentData
import ar.edu.itba.hci.wallx.data.model.payment.PaymentData
import ar.edu.itba.hci.wallx.data.model.payment.PendingPaymentData
import ar.edu.itba.hci.wallx.data.repository.PaymentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val paymentRepository: PaymentRepository
) : ViewModel() {

    val payments: StateFlow<List<PaymentData>> = paymentRepository.payments

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun refreshPayments(
        page: Int? = null,
        direction: String? = null,
        pending: Boolean? = null,
        method: String? = null,
        range: String? = null,
        role: String? = null,
        cardId: Int? = null
    ) {
        viewModelScope.launch {
            try {
                paymentRepository.refreshPayments(
                    page,
                    direction,
                    pending,
                    method,
                    range,
                    role,
                    cardId
                )
            } catch (e: Exception) {
                _error.value = "Error al cargar los pagos: ${e.message}"
            }
        }
    }

    fun pullPayment(
        newPayment: NewPaymentData,
        onSuccess: (PendingPaymentData) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                val response = paymentRepository.pullPayment(newPayment)
                onSuccess(response)
            } catch (e: Exception) {
                _error.value = "Error al solicitar pago: ${e.message}"
                onError(e)
            }
        }
    }

    fun pushPayment(
        uuid: String,
        cardId: Int?,
        onSuccess: (PendingPaymentData) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                val response = paymentRepository.pushPayment(uuid, cardId)
                onSuccess(response)
            } catch (e: Exception) {
                _error.value = "Error al completar pago: ${e.message}"
                onError(e)
            }
        }
    }

    fun transferEmail(
        email: String,
        cardId: Int?,
        payment: NewPaymentData,
        onSuccess: (PendingPaymentData) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                val response = paymentRepository.transferEmail(email, cardId, payment)
                onSuccess(response)
            } catch (e: Exception) {
                _error.value = "Error al transferir por email: ${e.message}"
                onError(e)
            }
        }
    }

    fun transferCvu(
        cvu: String,
        cardId: Int?,
        payment: NewPaymentData,
        onSuccess: (PendingPaymentData) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                val response = paymentRepository.transferCvu(cvu, cardId, payment)
                onSuccess(response)
            } catch (e: Exception) {
                _error.value = "Error al transferir por CVU: ${e.message}"
                onError(e)
            }
        }
    }

    fun transferAlias(
        alias: String,
        cardId: Int?,
        payment: NewPaymentData,
        onSuccess: (PendingPaymentData) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                val response = paymentRepository.transferAlias(alias, cardId, payment)
                onSuccess(response)
            } catch (e: Exception) {
                _error.value = "Error al transferir por alias: ${e.message}"
                onError(e)
            }
        }
    }

    fun getPaymentById(
        id: Int,
        onSuccess: (PaymentData) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                val payment = paymentRepository.getPaymentById(id)
                onSuccess(payment)
            } catch (e: Exception) {
                _error.value = "Error al obtener pago: ${e.message}"
                onError(e)
            }
        }
    }
}