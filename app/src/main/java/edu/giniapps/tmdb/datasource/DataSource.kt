package edu.giniapps.tmdb.datasource

import edu.giniapps.tmdb.models.MovieWithGenres
import edu.giniapps.tmdb.models.response.Genre
import edu.giniapps.tmdb.models.response.Movie
import kotlinx.coroutines.flow.Flow

interface DataSource {
    val popularMovies: Flow<Result<List<MovieWithGenres>>>

    val nowPlayingMovies: Flow<Result<List<MovieWithGenres>>>

    suspend fun insertMovies(movies: List<Movie>): Result<Unit>

    suspend fun insertGenres(genres: List<Genre>): Result<Unit>

    suspend fun deletePopularMovies(): Result<Unit>

    suspend fun deleteNowPlayingMovies(): Result<Unit>

    suspend fun deleteGenres(): Result<Unit>

    /*suspend fun getMovieById(id: Int): Flow<Result<MovieWithGenres?>>*/
}