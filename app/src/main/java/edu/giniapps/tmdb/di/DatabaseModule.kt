package edu.giniapps.tmdb.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.giniapps.tmdb.db.GenreDao
import edu.giniapps.tmdb.db.LocalDatabase
import edu.giniapps.tmdb.db.MovieDao
import edu.giniapps.tmdb.db.MovieGenreCrossRefDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    private const val DB_NAME = "test"

    @Provides
    fun provideMovieDao(database: LocalDatabase): MovieDao = database.movieDao()

    @Provides
    fun provideGenreDao(database: LocalDatabase): GenreDao = database.genreDao()

    @Provides
    fun provideMovieGenreCrossRefDao(database: LocalDatabase): MovieGenreCrossRefDao = database.movieGenreCrossRefDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): LocalDatabase {
        return Room.databaseBuilder(
            appContext,
            LocalDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()
    }
}