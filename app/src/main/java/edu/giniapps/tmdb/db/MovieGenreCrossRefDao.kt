package edu.giniapps.tmdb.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.giniapps.tmdb.models.response.MovieGenre
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieGenreCrossRefDao {

    @Query("SELECT * FROM MOVIE_GENRE")
    fun getAll(): Flow<List<MovieGenre>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movieGenres: List<MovieGenre>)
}