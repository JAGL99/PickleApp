package com.jagl.pickleapp.core.remote.source.character

import com.jagl.pickleapp.core.remote.model.GetCharacterById

fun interface CharacterRemoteDataSource {
    suspend fun getCharacterById(id: Long): Result<GetCharacterById.Response>
}