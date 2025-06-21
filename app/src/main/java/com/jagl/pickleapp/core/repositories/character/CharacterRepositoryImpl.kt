package com.jagl.pickleapp.core.repositories.character

import com.jagl.critiq.core.utils.dispatcherProvider.DispatcherProvider
import com.jagl.pickleapp.core.local.source.CharacterRoomSource
import com.jagl.pickleapp.core.remote.source.character.CharacterRemoteDataSource
import com.jagl.pickleapp.domain.model.CharacterDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val local: CharacterRoomSource,
    private val dispatcherProvider: DispatcherProvider
) : CharacterRepository {

    override fun getCharacterById(id: Long): Flow<CharacterDomain?> = flow {
        val localData = local.getById(id).first()
        if (localData != null) {
            emit(localData)
            return@flow
        }
        val data = remoteDataSource.getCharacterById(id)
        if (data.isFailure) {
            emit(null)
            return@flow
        }
        val remoteData = data.getOrThrow().toDomain()
        local.insert(remoteData)
        emit(remoteData)
    }.flowOn(dispatcherProvider.io)
}