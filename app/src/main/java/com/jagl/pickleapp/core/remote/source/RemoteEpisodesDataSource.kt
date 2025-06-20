package com.jagl.pickleapp.core.remote.source

import com.jagl.pickleapp.core.remote.model.GetEpisodes
import com.jagl.pickleapp.core.remote.model.GetEpisodes.RemoteEpisode

interface RemoteEpisodesDataSource {
    suspend fun getEpisodesByPage(page: Int): Result<GetEpisodes.Response>
    suspend fun getEpisodesByName(query: String): Result<List<RemoteEpisode>>
    suspend fun getEpisodesByEpisode(query: String): Result<List<RemoteEpisode>>
    suspend fun getEpisodeById(id: Int): Result<RemoteEpisode>
}