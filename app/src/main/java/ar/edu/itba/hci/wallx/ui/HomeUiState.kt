package ar.edu.itba.hci.wallx.ui

import ar.edu.itba.hci.wallx.data.model.Account
import ar.edu.itba.hci.wallx.data.model.User

data class HomeUiState(
    val isAuthenticated: Boolean = false,
    val isFetching: Boolean = false,
    val accountDetail: Account? = null,
    val userDetail: User? = null,
    val error: Error? = null
)
