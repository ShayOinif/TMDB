package edu.giniapps.tmdb.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.giniapps.tmdb.utils.TmdbLogger
import edu.giniapps.tmdb.utils.TmdbLoggerImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class LoggerModule {
    @Binds
    abstract fun bindTmdbLogger(impl: TmdbLoggerImpl): TmdbLogger
}