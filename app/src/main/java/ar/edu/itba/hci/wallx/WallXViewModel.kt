package ar.edu.itba.hci.wallx

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ar.edu.itba.hci.wallx.data.DataSourceException
import ar.edu.itba.hci.wallx.data.model.CompleteUser
import ar.edu.itba.hci.wallx.data.repository.AccountRepository
import ar.edu.itba.hci.wallx.data.repository.CardRepository
import ar.edu.itba.hci.wallx.data.repository.PaymentRepository
import ar.edu.itba.hci.wallx.data.repository.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ar.edu.itba.hci.wallx.data.model.Error
import ar.edu.itba.hci.wallx.data.network.model.card.NewCardData

class WallXViewModel (
    val sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository,
    private val cardRepository: CardRepository,
    private val paymentRepository: PaymentRepository
) : ViewModel() {

    private var accountStreamJob: Job? = null
    private var paymentsStreamJob: Job? = null
    private var cardsStreamJob: Job? = null
    private val _uiState =
        MutableStateFlow(HomeUiState(isAuthenticated = sessionManager.loadAuthToken() != null))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        if (_uiState.value.isAuthenticated) {
            observeAccountStream()
            observeCardsStream()
            observePaymentsStream()
        }
    }

    fun register(firstName : String, lastName: String, birthDate : String, email : String, password : String) = runOnViewModelScope(
        {
            userRepository.register(firstName, lastName, birthDate, email, password)
        },
        {
            state, _ -> state.copy()
        }
    )

    fun login(user : String, password : String) = runOnViewModelScope(
        {
            userRepository.login(user, password)
            observeAccountStream()
            observeCardsStream()
            observePaymentsStream()
        },
        {
            state, _ -> state.copy(
                isAuthenticated = true
            )
        }
    )

    fun logout() = runOnViewModelScope(
        {
            accountStreamJob?.cancel()
            cardsStreamJob?.cancel()
            userRepository.logout()
        },
        {
            state, _ -> state.copy(
                isAuthenticated = false,
                accountDetail = null,
                cardsDetail = null,
                completeUserDetail = null,
                paymentsDetail = null,
                error = null
            )
        }
    )

    fun verifyUser(code : String, email : String) = runOnViewModelScope(
        {
            userRepository.verifyUser(code, email)
        },
        {
            state, _ -> state.copy()
        }
    )

    fun getUser()  = runOnViewModelScope(
        {
            userRepository.getUser()
        },
        {
            state, response -> state.copy(completeUserDetail = response)

        }
    )

    fun addCard(card : NewCardData) = runOnViewModelScope(
        {
            cardRepository.addCard(card)
        },
        {
            state, _ -> state.copy()

        }
    )

    fun getSee() = runOnViewModelScope(
        {
            sessionManager.getSee()
        },
        { state, response ->
            state.copy(see = response)
        }
    )

    fun toggleSee() = runOnViewModelScope(
        {
            sessionManager.toggleSee()
        },
        {
            state, response -> state.copy(see = response)
        }
    )




    private fun observeAccountStream() {
        accountStreamJob = collectOnViewModelScope(
            accountRepository.accountDetailStream
        ) { state, response -> state.copy(accountDetail = response) }
    }

    private fun observePaymentsStream() {
        paymentsStreamJob = collectOnViewModelScope(
            paymentRepository.getAllPaymentsStream
        ) { state, response -> state.copy(paymentsDetail = response) }
    }

    private fun observeCardsStream() {
        cardsStreamJob = collectOnViewModelScope(
            cardRepository.cardsStream
        ) { state, response -> state.copy(cardsDetail = response) }
    }

    private fun <T> collectOnViewModelScope(
        flow: Flow<T>,
        updateState: (HomeUiState, T) -> HomeUiState
    ) = viewModelScope.launch {
        flow
            .distinctUntilChanged()
            .catch { e ->
                _uiState.update { currentState ->
                    currentState.copy(
                        accountDetail = null,
                        error = handleError(e)
                    )
                }
            }
            .collect { response ->
                _uiState.update { currentState ->
                    updateState(
                        currentState,
                        response
                    )
                }
            }
    }

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (HomeUiState, R) -> HomeUiState
    ): Job = viewModelScope.launch {
        _uiState.update { currentState -> currentState.copy(isFetching = true, error = null) }
        runCatching {
            block()
        }.onSuccess { response ->
            _uiState.update { currentState ->
                updateState(
                    currentState,
                    response
                ).copy(isFetching = false)
            }
        }.onFailure { e ->
            _uiState.update { currentState ->
                currentState.copy(
                    isFetching = false,
                    accountDetail = null,
                    error = handleError(e)
                )
            }
            Log.e(TAG, "Coroutine execution failed", e)
        }
    }

    private fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            Error(e.code, e.message ?: "")
        } else {
            Error(null, e.message ?: "")
        }
    }

    companion object {
        const val TAG = "UI Layer"

        fun provideFactory(
            application: WallXApplication
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return WallXViewModel(
                    application.sessionManager,
                    application.userRepository,
                    application.accountRepository,
                    application.cardRepository,
                    application.paymentRepository
                ) as T
            }
        }
    }

}