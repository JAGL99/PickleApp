package com.jagl.pickleapp.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagl.pickleapp.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
            val lastValue = _uiState.value.data
            delay(5000L)
            characterRepository.getCharacters()
                .catch { e ->
                    Timber.e(e)
                    _uiState.update { it.copy(errorMessage = e.message) }
                    emit(lastValue)
                }
                .collectLatest { data ->
                    _uiState.update {
                        it.copy(
                            data = data,
                            isLoading = false
                        )
                    }
                }
        }

    }

    fun getMoreCharacters() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            delay(500L)
            val nextPage = _uiState.value.page + 1
            val currenteData = _uiState.value.data


            if (currenteData.count() <= 20 * nextPage) {
                val result = characterRepository.requestMoreCharacters(nextPage)
                if (result.isFailure) {
                    _uiState.update {
                        it.copy(
                            errorMessage = result.exceptionOrNull()?.message ?: "Unkow error"
                        )
                    }
                    getCharacters()
                    return@launch
                }

            }

            _uiState.update {
                it.copy(page = nextPage)
            }
            getCharacters()
        }
    }

    fun onGoToDetail(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(500L)
            val event = UiEvent.GoToCharacterDetails(id)
            _uiEvent.emit(event)
            _uiState.update { it.copy(isLoading = false) }
        }

    }
}