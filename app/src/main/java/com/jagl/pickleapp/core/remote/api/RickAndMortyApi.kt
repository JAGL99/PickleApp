package com.jagl.pickleapp.core.remote.api

import com.jagl.pickleapp.core.remote.model.GetCharacters
import com.jagl.pickleapp.core.remote.model.GetEpisodes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") id: Int,
    ): GetCharacters.Response

    @GET("episode/")
    suspend fun getEpisodes(
        @Query("page") page: Int? = null,
        @Query("name") name: String? = null,
        @Query("episode") episode: String? = null
    ): Response<GetEpisodes.Response>

    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") id: Int): Response<GetEpisodes.RemoteEpisode>
}
