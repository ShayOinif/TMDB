package edu.giniapps.tmdb.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.giniapps.tmdb.networking.RemoteApi
import edu.giniapps.tmdb.networking.RemoteApiImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class RemoteApiModule {
    @Binds
    abstract fun bindRemoteApi(impl: RemoteApiImpl): RemoteApi
}