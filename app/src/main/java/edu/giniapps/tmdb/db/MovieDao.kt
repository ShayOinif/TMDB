package edu.giniapps.tmdb.db

import androidx.room.*
import edu.giniapps.tmdb.models.response.Movie
import edu.giniapps.tmdb.models.response.Movie.Companion.NOW_PLAYING
import edu.giniapps.tmdb.models.response.Movie.Companion.POPULAR
import edu.giniapps.tmdb.models.response.MovieWithGenres
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Transaction
    @Query("SELECT * FROM MOVIES WHERE category = :category ORDER BY popularity DESC")
    fun getPopularMovies(category: Int = POPULAR): Flow<List<MovieWithGenres>>

    @Transaction
    @Query("SELECT * FROM MOVIES WHERE category = :category ORDER BY release_date DESC")
    fun getNowPlayingMovies(category: Int = NOW_PLAYING): Flow<List<MovieWithGenres>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Query("DELETE FROM MOVIES WHERE category = :category")
    suspend fun deleteByCategory(category: Int)
}