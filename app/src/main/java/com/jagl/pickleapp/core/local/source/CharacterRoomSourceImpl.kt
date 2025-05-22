package com.jagl.pickleapp.core.local.source

import com.jagl.pickleapp.core.local.daos.CharacterDao
import com.jagl.pickleapp.core.local.entities.CharacterEntity
import com.jagl.pickleapp.domain.model.CharacterDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterRoomSourceImpl @Inject constructor(
    private val characterDao: CharacterDao
) : CharacterRoomSource {

    override fun getAll(): Flow<List<CharacterDomain>> {
        return characterDao.getAllCharacters().map { it.map { entity -> entity.toDomain() } }
    }

    override fun getById(id: Long): Flow<CharacterDomain?> {
        return characterDao.getCharacterById(id)
            .map { entity -> entity?.toDomain() }
    }

    override suspend fun insertAll(domain: List<CharacterDomain>) {
        val entities = domain.map { character -> CharacterEntity.fromDomain(character) }
        characterDao.insertAllCharacter(entities)
    }

    override suspend fun insert(domain: CharacterDomain) {
        characterDao.insertCharacter(CharacterEntity.fromDomain(domain))
    }

    override suspend fun deleteAll() {
        characterDao.deleteAllMedia()
    }

    override suspend fun delete(domain: CharacterDomain) {
        characterDao.deleteMedia(CharacterEntity.fromDomain(domain))
    }

}