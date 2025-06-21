package com.jagl.pickleapp.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagl.pickleapp.core.repository.episode.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val episodeRepository: EpisodeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getEpisodes()
    }

    private fun getEpisodes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val lastValue = _uiState.value.data
            val page = _uiState.value.page + 1
            try {
                val data = episodeRepository.getEpisodesByPage(page)
                _uiState.update {
                    it.copy(
                        data = lastValue + data,
                        isLoading = false,
                        errorMessage = null,
                        page = page
                    )
                }
            } catch (e: Exception) {
                Timber.e(e, "Error fetching episodes")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Unknown error",
                        data = lastValue
                    )
                }
            }


        }

    }

    fun getMoreEpisodes() {
        viewModelScope.launch {
            val nextPage = _uiState.value.page + 1
            if (_uiState.value.errorMessage != null) {
                val result = episodeRepository.getEpisodesByPage(nextPage)
                if (result.isEmpty()) return@launch
                else {
                    _uiState.update {
                        it.copy(
                            errorMessage = null,
                            isLoading = true
                        )
                    }
                }
            }
            val currenteData = _uiState.value.data
            if (currenteData.count() <= 20 * nextPage) {
                val result = episodeRepository.getEpisodesByPage(nextPage)
                if (result.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            errorMessage = "Unkow error",
                            isLoading = false
                        )
                    }
                    return@launch
                }

            }
            _uiState.update { it.copy(page = nextPage) }
            getEpisodes()
        }
    }

    fun onGoToDetail(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val event = UiEvent.GoToEpisodeDetails(id)
            _uiEvent.emit(event)
            _uiState.update { it.copy(isLoading = false) }
        }

    }
}