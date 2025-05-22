package com.jagl.pickleapp.features.detail

import com.jagl.pickleapp.domain.model.CharacterDomain

data class UiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val data: CharacterDomain? = null
)