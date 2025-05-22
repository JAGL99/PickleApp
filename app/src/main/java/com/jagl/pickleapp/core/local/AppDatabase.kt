package com.jagl.pickleapp.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jagl.pickleapp.core.local.daos.CharacterDao
import com.jagl.pickleapp.core.local.entities.CharacterEntity

@Database(
    entities = [CharacterEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    companion object {
        const val DATABASE_NAME = "pickleapp_database"
    }
}