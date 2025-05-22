package com.jagl.pickleapp.features.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagl.pickleapp.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val characterId: Long = savedStateHandle.get<Long>(CHARACTER_ID) ?: 0L

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        getCharacterDetail(characterId)
    }

    private fun getCharacterDetail(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            characterRepository.getCharacterById(id)
                .catch { e -> _uiState.update { it.copy(errorMessage = e.message) } }
                .collect { data ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            data = data
                        )
                    }
                }
        }
    }


    companion object {
        const val CHARACTER_ID = "characterId"
    }
}
