package com.jagl.pickleapp.core.repositories.episodes

import com.jagl.pickleapp.core.remote.model.GetEpisodes

interface EpisodeRepository {
    fun getEpisodesByPage(page: Int): List<GetEpisodes.RemoteEpisode>

    fun getEpisodesByQuery(
        query: String,
        type: String
    ): List<GetEpisodes.RemoteEpisode>

    fun getEpisodeById(id: Long): GetEpisodes.RemoteEpisode
}