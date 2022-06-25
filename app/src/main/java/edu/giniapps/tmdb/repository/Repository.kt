package edu.giniapps.tmdb.repository

import edu.giniapps.tmdb.models.response.MovieWithGenres
import kotlinx.coroutines.flow.Flow

interface Repository {

    val initialized: Boolean

    suspend fun init(): Result<Unit>

    suspend fun reignite(): Result<Unit>

    suspend fun refreshPopularMovies(): Result<Unit>

    suspend fun refreshNowPlayingMovies(): Result<Unit>

    val popularMovies: Flow<Result<List<MovieWithGenres>>>

    val nowPlayingMovies: Flow<Result<List<MovieWithGenres>>>
}