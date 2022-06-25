package edu.giniapps.tmdb.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.giniapps.tmdb.datasource.DataSource
import edu.giniapps.tmdb.datasource.DatasourceImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindDataSource(impl: DatasourceImpl): DataSource
}