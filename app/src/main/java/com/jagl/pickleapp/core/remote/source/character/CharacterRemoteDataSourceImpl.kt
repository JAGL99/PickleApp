package com.jagl.pickleapp.core.remote.source.character

import com.jagl.pickleapp.core.remote.api.RickAndMortyApi
import com.jagl.pickleapp.core.remote.model.GetCharacterById
import javax.inject.Inject


class CharacterRemoteDataSourceImpl @Inject constructor(
    private val api: RickAndMortyApi
) : CharacterRemoteDataSource {

    override suspend fun getCharacterById(id: Long): Result<GetCharacterById.Response> {
        val request = api.getCharacterById(id.toInt())
        return if (request.isSuccessful && request.body() != null) {
            Result.success(request.body()!!)
        } else {
            Result.failure(
                Exception("Failed to fetch episode by ID: ${request.errorBody()?.string()}")
            )
        }
    }
}