package com.jagl.pickleapp.features.home

sealed class UiEvent {
    data class GoToEpisodeDetails(val episodeId: Long) : UiEvent()
}