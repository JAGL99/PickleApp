package com.jagl.pickleapp.features.home

import com.jagl.pickleapp.domain.model.CharacterDomain

data class UiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val page: Int = 1,
    val data: List<CharacterDomain> = emptyList()
)