package com.jagl.pickleapp.core.repository.character

import com.jagl.pickleapp.domain.model.CharacterDomain
import kotlinx.coroutines.flow.Flow

fun interface CharacterRepository {
    fun getCharacterById(id: Long): Flow<CharacterDomain?>
}