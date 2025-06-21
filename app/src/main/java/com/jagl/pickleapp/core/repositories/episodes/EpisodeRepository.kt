package com.jagl.pickleapp.core.repositories.episodes

import com.jagl.pickleapp.domain.model.EpisodeDomain

interface EpisodeRepository {
    fun getEpisodesByPage(page: Int): List<EpisodeDomain>

    fun getEpisodesByQuery(
        query: String,
        type: String
    ): List<EpisodeDomain>

    fun getEpisodeById(id: Long): EpisodeDomain
}