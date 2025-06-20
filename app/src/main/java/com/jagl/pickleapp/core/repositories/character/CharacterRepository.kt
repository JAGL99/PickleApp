package com.jagl.pickleapp.core.repositories.character

import com.jagl.pickleapp.domain.model.CharacterDomain
import com.jagl.pickleapp.domain.model.Info
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharacters(): Flow<List<CharacterDomain>>

    suspend fun requestMoreCharacters(pageToLoad: Int): Result<Info>

    fun getCharacterById(id: Long): Flow<CharacterDomain?>

}