package com.jagl.pickleapp.core.repositories.episodes

import com.jagl.critiq.core.utils.dispatcherProvider.DispatcherProvider
import com.jagl.pickleapp.core.remote.model.GetEpisodes
import com.jagl.pickleapp.core.remote.source.RemoteEpisodesDataSource
import com.jagl.pickleapp.core.utils.extensions.SEARCH_BY_EPISODE
import com.jagl.pickleapp.core.utils.extensions.SEARCH_BY_NAME
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteEpisodesDataSource,
    private val dispatcherProvider: DispatcherProvider
) : EpisodeRepository {

    override fun getEpisodesByPage(page: Int): List<GetEpisodes.RemoteEpisode> {
        val response = runBlocking(
            context = dispatcherProvider.io
        ) {
            remoteDataSource.getEpisodesByPage(page).getOrThrow().episodes
        }
        return response.orEmpty()
    }

    override fun getEpisodesByQuery(
        query: String,
        type: String
    ): List<GetEpisodes.RemoteEpisode> {
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
        return response
    }

    override fun getEpisodeById(id: Long): GetEpisodes.RemoteEpisode {
        val response = runBlocking(
            context = dispatcherProvider.io
        ) {
            remoteDataSource.getEpisodeById(id.toInt())
        }
        return response.getOrThrow()
    }
}