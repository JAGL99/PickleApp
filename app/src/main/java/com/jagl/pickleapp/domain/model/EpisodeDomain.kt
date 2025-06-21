package com.jagl.pickleapp.domain.model

data class EpisodeDomain(
    val id: Long,
    val name: String,
    val airDate: String,
    val episode: String,
    val charactersInEpisode: List<Long>,
    val url: String,
    val created: String
)
