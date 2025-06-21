package com.jagl.pickleapp.features.home

import com.jagl.pickleapp.domain.model.EpisodeDomain

data class UiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val page: Int = 0,
    val data: List<EpisodeDomain> = emptyList()
)