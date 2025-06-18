package ar.edu.itba.hci.wallx.data.repository

import ar.edu.itba.hci.wallx.data.model.Payment
import ar.edu.itba.hci.wallx.data.network.PaymentRemoteDataSource
import ar.edu.itba.hci.wallx.data.network.model.payment.NewPaymentData
import ar.edu.itba.hci.wallx.data.network.model.payment.PaymentData
import ar.edu.itba.hci.wallx.data.network.model.payment.PendingPaymentData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PaymentRepository (
    private val remoteDataSource: PaymentRemoteDataSource
) {

    val getAllPaymentsStream: Flow<List<Payment>> =
        remoteDataSource.getAllPaymentsStream
            .map { it.asModel() }

    suspend fun pullPayment(newPaymentData : NewPaymentData) : PendingPaymentData{
        return remoteDataSource.pullPayment(newPaymentData)
    }

    suspend fun pushPayment(uuid : String, cardId : Int) : PendingPaymentData{
        return remoteDataSource.pushPayment(
            uuid = uuid,
            cardId = cardId
        )
    }

    suspend fun transferEmail(email : String, cardId: Int, newPaymentData : NewPaymentData) : PendingPaymentData{
        return remoteDataSource.transferEmail(
            email = email,
            cardId = cardId,
            newPaymentData = newPaymentData
        )
    }

    suspend fun transferCvu(cvu : String, cardId: Int, newPaymentData : NewPaymentData) : PendingPaymentData{
        return remoteDataSource.transferCvu(
            cvu = cvu,
            cardId = cardId,
            newPaymentData = newPaymentData
        )
    }

    suspend fun transferAlias(alias : String, cardId: Int, newPaymentData : NewPaymentData) : PendingPaymentData{
        return remoteDataSource.transferAlias(
            alias = alias,
            cardId = cardId,
            newPaymentData = newPaymentData
        )
    }

    suspend fun getPayment(id : Int) : PaymentData {
        return remoteDataSource.getPayment(id)
    }

}