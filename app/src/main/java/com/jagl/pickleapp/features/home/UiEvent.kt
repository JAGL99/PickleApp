package com.jagl.pickleapp.features.home

sealed class UiEvent {
    data class GoToCharacterDetails(val characterId: Long) : UiEvent()
}