package edu.giniapps.tmdb.db

import androidx.room.*
import edu.giniapps.tmdb.models.MovieWithGenres
import edu.giniapps.tmdb.models.response.Movie
import edu.giniapps.tmdb.models.response.Movie.Companion.NOW_PLAYING
import edu.giniapps.tmdb.models.response.Movie.Companion.POPULAR
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

    @Query("DELETE FROM MOVIES WHERE category = :category AND favorite = 0")
    suspend fun deleteByCategory(category: Int)

    /*@Query("SELECT * FROM MOVIES WHERE id = :id")
    suspend fun getMovieById(id: Int): Flow<MovieWithGenres>*/
}