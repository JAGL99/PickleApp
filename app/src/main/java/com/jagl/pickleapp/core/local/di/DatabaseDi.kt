package com.jagl.pickleapp.core.local.di

import android.content.Context
import androidx.room.Room
import com.jagl.pickleapp.core.local.AppDatabase
import com.jagl.pickleapp.core.local.daos.CharacterDao
import com.jagl.pickleapp.core.local.source.CharacterRoomSourceImpl
import com.jagl.pickleapp.core.local.source.CharacterRoomSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseDi {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideCharacterDao(
        appDatabase: AppDatabase
    ): CharacterDao {
        return appDatabase.characterDao()
    }

    @Singleton
    @Provides
    fun provideCharacterRoomSource(
        characterDao: CharacterDao
    ): CharacterRoomSource {
        return CharacterRoomSourceImpl(characterDao)
    }

}