package com.jagl.pickleapp.core.repositories.di

import com.jagl.critiq.core.utils.dispatcherProvider.DispatcherProvider
import com.jagl.pickleapp.core.local.source.CharacterRoomSource
import com.jagl.pickleapp.core.remote.api.RickAndMortyApi
import com.jagl.pickleapp.core.remote.source.RemoteEpisodesDataSource
import com.jagl.pickleapp.core.repositories.character.CharacterRepository
import com.jagl.pickleapp.core.repositories.character.CharacterRepositoryImpl
import com.jagl.pickleapp.core.repositories.episodes.EpisodeRepository
import com.jagl.pickleapp.core.repositories.episodes.EpisodeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesDi {

    @Singleton
    @Provides
    fun provideCharacterRepository(
        api: RickAndMortyApi,
        database: CharacterRoomSource,
        dispatcherProvider: DispatcherProvider
    ): CharacterRepository {
        return CharacterRepositoryImpl(api, database, dispatcherProvider)
    }

    @Singleton
    @Provides
    fun provideEpisodeRepository(
        remoteEpisodesDataSource: RemoteEpisodesDataSource,
        dispatcherProvider: DispatcherProvider
    ): EpisodeRepository {
        return EpisodeRepositoryImpl(
            remoteEpisodesDataSource, dispatcherProvider
        )
    }
}