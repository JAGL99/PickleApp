package com.jagl.pickleapp.core.local.source

import com.jagl.pickleapp.domain.model.CharacterDomain
import kotlinx.coroutines.flow.Flow

interface CharacterRoomSource {
    fun getAll(): Flow<List<CharacterDomain>>
    fun getById(id: Long): Flow<CharacterDomain?>
    suspend fun insertAll(domain: List<CharacterDomain>)
    suspend fun insert(domain: CharacterDomain)
    suspend fun deleteAll()
    suspend fun delete(domain: CharacterDomain)
}