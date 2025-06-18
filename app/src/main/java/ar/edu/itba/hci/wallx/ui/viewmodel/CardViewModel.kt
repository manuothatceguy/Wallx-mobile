package ar.edu.itba.hci.wallx.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.itba.hci.wallx.data.network.model.card.CardData
import ar.edu.itba.hci.wallx.data.network.model.card.NewCardData
import ar.edu.itba.hci.wallx.data.repository.CardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardViewModel(
    private val cardRepository: CardRepository // Inyección manual o con Hilt
) : ViewModel() {

    val cards: StateFlow<List<CardData>> = cardRepository.cards

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun refreshCards() {
        viewModelScope.launch {
            try {
                cardRepository.refreshCards()
            } catch (e: Exception) {
                _error.value = "Error al cargar las tarjetas: ${e.message}"
            }
        }
    }

    fun addCard(
        newCard: NewCardData,
        onSuccess: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                cardRepository.addCard(newCard)
                onSuccess()
            } catch (e: Exception) {
                _error.value = "Error al agregar tarjeta: ${e.message}"
                onError(e)
            }
        }
    }

    fun deleteCard(id: Int, onSuccess: () -> Unit = {}, onError: (Throwable) -> Unit = {}) {
        viewModelScope.launch {
            try {
                cardRepository.deleteCard(id)
                onSuccess()
            } catch (e: Exception) {
                _error.value = "Error al eliminar tarjeta: ${e.message}"
                onError(e)
            }
        }
    }
}