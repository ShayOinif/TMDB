package edu.giniapps.tmdb.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.giniapps.tmdb.networking.NetworkStatusChecker
import edu.giniapps.tmdb.networking.NetworkStatusCheckerImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkStatusCheckerModule {
    @Binds
    abstract fun bindNetStatusChecker(impl: NetworkStatusCheckerImpl): NetworkStatusChecker
}