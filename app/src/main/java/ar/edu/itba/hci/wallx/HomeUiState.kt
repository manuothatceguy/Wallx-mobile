package ar.edu.itba.hci.wallx

import ar.edu.itba.hci.wallx.data.model.Account
import ar.edu.itba.hci.wallx.data.model.Card
import ar.edu.itba.hci.wallx.data.model.CompleteUser
import ar.edu.itba.hci.wallx.data.model.Payment
import ar.edu.itba.hci.wallx.data.model.Error
import ar.edu.itba.hci.wallx.ui.components.errorManager

data class HomeUiState(
    val isAuthenticated: Boolean = false,
    val isFetching: Boolean = false,
    val accountDetail: Account? = null,
    val completeUserDetail: CompleteUser? = null,
    val cardsDetail: List<Card>? = listOf(),
    val paymentsDetail: List<Payment>? = listOf(),
    val error: Error? = null,
    val see : Boolean = false,
    val currentPayment : Payment? = null
)
