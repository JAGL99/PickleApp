package com.jagl.pickleapp.core.repositories.character

import com.jagl.pickleapp.domain.model.CharacterDomain
import kotlinx.coroutines.flow.Flow

fun interface CharacterRepository {
    fun getCharacterById(id: Long): Flow<CharacterDomain?>
}