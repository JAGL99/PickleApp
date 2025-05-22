package com.jagl.pickleapp.core.remote.api

import com.jagl.pickleapp.core.remote.model.GetCharacters
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

fun interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") id: Int,
    ): GetCharacters.Response

}
