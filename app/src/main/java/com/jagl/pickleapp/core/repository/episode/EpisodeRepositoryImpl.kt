package com.jagl.pickleapp.core.repository.episode

import com.jagl.critiq.core.utils.dispatcherProvider.DispatcherProvider
import com.jagl.pickleapp.core.local.source.episode.EpisodeLocalDataSource
import com.jagl.pickleapp.core.remote.model.GetEpisodes
import com.jagl.pickleapp.core.remote.source.episode.EpisodesRemoteDataSource
import com.jagl.pickleapp.core.utils.extensions.SEARCH_BY_EPISODE
import com.jagl.pickleapp.core.utils.extensions.SEARCH_BY_NAME
import com.jagl.pickleapp.domain.model.EpisodeDomain
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val remoteDataSource: EpisodesRemoteDataSource,
    private val localDataSource: EpisodeLocalDataSource,
    private val dispatcherProvider: DispatcherProvider
) : EpisodeRepository {

    override suspend fun getEpisodesByPage(page: Int): List<EpisodeDomain> {
        val cachedEpisodes = localDataSource.getByPage(page).firstOrNull()
        if (!cachedEpisodes.isNullOrEmpty()) {
            return cachedEpisodes
        }
        val response = runBlocking(
            context = dispatcherProvider.io
        ) {
            remoteDataSource.getEpisodesByPage(page)
                .getOrThrow().episodes?.map(GetEpisodes.RemoteEpisode::toDomain)
                ?.map { it.copy(page = page) } ?: emptyList()
        }
        if (response.isNotEmpty()) {
            localDataSource.insertAll(response)
        }
        return response
    }

    override suspend fun getEpisodesByQuery(
        query: String,
        type: String
    ): List<EpisodeDomain> {
        val response = runBlocking(
            context = dispatcherProvider.io
        ) {
            when (type) {
                String.SEARCH_BY_NAME -> remoteDataSource.getEpisodesByName(query).getOrThrow()
                String.SEARCH_BY_EPISODE -> remoteDataSource.getEpisodesByEpisode(query)
                    .getOrThrow()

                else -> emptyList()
            }
        }
        return response.map(GetEpisodes.RemoteEpisode::toDomain)
    }

    override suspend fun getEpisodeById(id: Long): EpisodeDomain {
        val cachedEpisode = localDataSource.getById(id).firstOrNull()
        if (cachedEpisode != null) {
            return cachedEpisode
        }
        val response = runBlocking(
            context = dispatcherProvider.io
        ) {
            remoteDataSource.getEpisodeById(id.toInt()).getOrThrow().toDomain()
        }
        localDataSource.insert(response)
        return response
    }
}