package ar.edu.itba.hci.wallx.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.itba.hci.wallx.data.model.user.ChangePasswordData
import ar.edu.itba.hci.wallx.data.model.user.CredentialsData
import ar.edu.itba.hci.wallx.data.model.user.NewUserData
import ar.edu.itba.hci.wallx.data.model.user.UserData
import ar.edu.itba.hci.wallx.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository // Inyectado con Hilt o manualmente
) : ViewModel() {

    val user: StateFlow<UserData?> = userRepository.user

    private val _userResult = MutableStateFlow<Result<Unit>?>(null)
    val userResult: StateFlow<Result<Unit>?> = _userResult

    fun refreshUser() {
        viewModelScope.launch {
            userRepository.refreshUser()
        }
    }

    fun login(
        credentials: CredentialsData,
        onSuccess: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                userRepository.login(credentials)
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }

    fun register(
        newUser: NewUserData,
        onSuccess: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                userRepository.register(newUser)
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun resendVerification(email: String, onError: (Throwable) -> Unit = {}) {
        viewModelScope.launch {
            try {
                userRepository.resendVerification(email)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun verifyUser(
        code: String,
        email: String,
        onSuccess: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                userRepository.verifyUser(code, email)
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun resetPassword(email: String, onComplete: (Result<Unit>) -> Unit = {}) {
        viewModelScope.launch {
            try {
                userRepository.resetPassword(email)
                onComplete(Result.success(Unit))
            } catch (e: Exception) {
                onComplete(Result.failure(e))
            }
        }
    }

    fun changePassword(data: ChangePasswordData, onComplete: (Result<Unit>) -> Unit = {}) {
        viewModelScope.launch {
            try {
                userRepository.changePassword(data)
                onComplete(Result.success(Unit))
            } catch (e: Exception) {
                onComplete(Result.failure(e))
            }
        }
    }
}