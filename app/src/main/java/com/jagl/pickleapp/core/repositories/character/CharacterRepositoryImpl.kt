package com.jagl.pickleapp.core.repositories.character

import com.jagl.critiq.core.utils.dispatcherProvider.DispatcherProvider
import com.jagl.pickleapp.core.local.source.CharacterRoomSource
import com.jagl.pickleapp.core.remote.api.RickAndMortyApi
import com.jagl.pickleapp.core.utils.extensions.getLastNumberOfUrl
import com.jagl.pickleapp.domain.model.CharacterDomain
import com.jagl.pickleapp.domain.model.Info
import com.jagl.pickleapp.domain.model.PaginatedCharacters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val remote: RickAndMortyApi,
    private val local: CharacterRoomSource,
    private val dispatcherProvider: DispatcherProvider
) : CharacterRepository {

    override fun getCharacters(): Flow<List<CharacterDomain>> = flow {
        local.getAll().collect {
            if (it.isEmpty()) {
                val response = remote.getCharacters(1)
                val paginateCharacters = PaginatedCharacters(
                    info = Info(
                        pages = response.info?.pages ?: 1,
                    ),
                    characters = response.characters?.map {
                        CharacterDomain(
                            id = it.id ?: 0,
                            name = it.name ?: "",
                            status = it.status ?: "",
                            species = it.species ?: "",
                            image = it.image ?: "",
                            origin = it.origin?.name ?: "",
                            location = it.location?.name ?: "",
                            episodes = it.episode?.map { it.getLastNumberOfUrl() } ?: emptyList()
                        )
                    }.orEmpty()
                )
                local.insertAll(paginateCharacters.characters)
                emit(paginateCharacters.characters)
            } else {
                emit(it)
            }

        }
    }.flowOn(dispatcherProvider.default)

    override suspend fun requestMoreCharacters(pageToLoad: Int): Result<Info> {
        val response = try {
            val (apiInfo, apiCharacters) = remote.getCharacters(pageToLoad)
            val paginateCharacters = PaginatedCharacters(
                info = Info(
                    pages = apiInfo?.pages ?: 1,
                ),
                characters = apiCharacters?.map {
                    CharacterDomain(
                        id = it.id ?: 0,
                        name = it.name ?: "",
                        status = it.status ?: "",
                        species = it.species ?: "",
                        image = it.image ?: "",
                        origin = it.origin?.name ?: "",
                        location = it.location?.name ?: "",
                        episodes = it.episode?.map { it.getLastNumberOfUrl() } ?: emptyList()
                    )
                }.orEmpty()
            )
            local.insertAll(paginateCharacters.characters)
            paginateCharacters.info
        } catch (e: HttpException) {
            return Result.failure(Exception("Unknown error"))
        } catch (e: IOException) {
            return Result.failure(Exception("Api unknown error"))
        }
        return Result.success(response)
    }

    override fun getCharacterById(id: Long): Flow<CharacterDomain?> = flow {
        local.getById(id).collect { emit(it) }
    }.flowOn(dispatcherProvider.default)
}