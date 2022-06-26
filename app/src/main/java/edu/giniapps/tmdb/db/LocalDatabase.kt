package edu.giniapps.tmdb.db

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.giniapps.tmdb.models.MovieGenre
import edu.giniapps.tmdb.models.response.Genre
import edu.giniapps.tmdb.models.response.Movie

@Database(entities = [Movie::class, Genre::class, MovieGenre::class], version = 4, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun genreDao(): GenreDao

    abstract fun movieGenreCrossRefDao(): MovieGenreCrossRefDao
}