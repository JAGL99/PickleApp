package com.jagl.pickleapp.features.episode_detail

import com.jagl.pickleapp.core.remote.model.GetEpisodes

data class UiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val data: GetEpisodes.RemoteEpisode? = null
)