package ar.edu.itba.hci.wallx.data.network

import ar.edu.itba.hci.wallx.data.network.api.PaymentApiService
import ar.edu.itba.hci.wallx.data.network.model.payment.CompletePaymentData
import ar.edu.itba.hci.wallx.data.network.model.payment.NewPaymentData
import ar.edu.itba.hci.wallx.data.network.model.payment.PaymentData
import ar.edu.itba.hci.wallx.data.network.model.payment.PendingPaymentData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PaymentRemoteDataSource(
    private val paymentApiService: PaymentApiService

) : RemoteDataSource() {
    val getAllPaymentsStream : Flow <CompletePaymentData> = flow {
        while(true) {
            val payments = handleApiResponse {
                paymentApiService.getAllPayments()
            }
            emit(payments)
            delay(DELAY)
        }
    }

    suspend fun pullPayment(newPaymentData : NewPaymentData) : PendingPaymentData {
         return handleApiResponse {
            paymentApiService.pullPayment(newPaymentData)
        }
    }

    suspend fun pushPayment(uuid : String, cardId : Int?) : PendingPaymentData {
        return handleApiResponse {
            paymentApiService.pushPayment(uuid, cardId)
        }
    }

    suspend fun transferEmail(email : String, cardId : Int?, newPaymentData : NewPaymentData) : PendingPaymentData {
        return handleApiResponse {
            paymentApiService.transferEmail(email, cardId, newPaymentData)
        }
    }

    suspend fun transferCvu(cvu : String, cardId : Int?, newPaymentData : NewPaymentData) : PendingPaymentData {
        return handleApiResponse {
            paymentApiService.transferCvu(cvu, cardId, newPaymentData)
        }
    }

    suspend fun transferAlias(alias : String, cardId : Int?, newPaymentData : NewPaymentData) : PendingPaymentData {
        return handleApiResponse {
            paymentApiService.transferAlias(alias, cardId, newPaymentData)
        }
    }

    suspend fun getPayment(id : Int) : PaymentData {
        return handleApiResponse {
            paymentApiService.getPaymentById(id)
        }
    }

    companion object {
        const val DELAY: Long = 3000
    }
}