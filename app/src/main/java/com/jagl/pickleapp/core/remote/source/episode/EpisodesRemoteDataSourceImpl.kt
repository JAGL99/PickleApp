package com.jagl.pickleapp.core.remote.source.episode

import com.jagl.pickleapp.core.remote.api.RickAndMortyApi
import com.jagl.pickleapp.core.remote.model.GetEpisodes
import javax.inject.Inject


class EpisodesRemoteDataSourceImpl @Inject constructor(
    private val api: RickAndMortyApi
) : EpisodesRemoteDataSource {

    override suspend fun getEpisodesByPage(page: Int): Result<GetEpisodes.Response> {
        val request = api.getEpisodes(page = page)
        return if (request.isSuccessful && request.body() != null) {
            Result.success(request.body()!!)
        } else {
            Result.failure(Exception("Failed to fetch episodes: ${request.errorBody()?.string()}"))
        }

    }

    override suspend fun getEpisodesByName(query: String): Result<List<GetEpisodes.RemoteEpisode>> {
        val request = api.getEpisodes(name = query)
        return if (request.isSuccessful && request.body()?.episodes != null) {
            Result.success(request.body()!!.episodes!!)
        } else {
            Result.failure(
                Exception(
                    "Failed to fetch episodes by name: ${
                        request.errorBody()?.string()
                    }"
                )
            )
        }
    }

    override suspend fun getEpisodesByEpisode(query: String): Result<List<GetEpisodes.RemoteEpisode>> {
        val request = api.getEpisodes(episode = query)
        return if (request.isSuccessful && request.body()?.episodes != null) {
            Result.success(request.body()!!.episodes!!)
        } else {
            Result.failure(
                Exception(
                    "Failed to fetch episodes by episode: ${
                        request.errorBody()?.string()
                    }"
                )
            )
        }
    }

    override suspend fun getEpisodeById(id: Int): Result<GetEpisodes.RemoteEpisode> {
        val request = api.getEpisodeById(id)
        return if (request.isSuccessful && request.body() != null) {
            Result.success(request.body()!!)
        } else {
            Result.failure(
                Exception(
                    "Failed to fetch episode by ID: ${
                        request.errorBody()?.string()
                    }"
                )
            )
        }
    }
}