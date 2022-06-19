package edu.giniapps.day23.networking

import edu.giniapps.day23.models.response.*

// All the apis in one facade.
interface RemoteApi {
    // Movies:
    suspend fun popularMovies(page: Int = 1): Result<ItemWrapper<Movie>>
    suspend fun topRatedMovies(page: Int = 1): Result<ItemWrapper<Movie>>
    suspend fun latestMovies(page: Int = 1): Result<ItemWrapper<Movie>>
    suspend fun searchMovies(page: Int = 1, query: String): Result<ItemWrapper<Movie>>
    suspend fun movieTrailers(movieId: Int = 1): Result<VideoWrapper>
    suspend fun movieCredits(movieId: Int): Result<CreditWrapper>

    // TV:
    suspend fun popularTvShows(page: Int = 1): Result<ItemWrapper<TVShow>>
    suspend fun topRatedTvShows(page: Int = 1): Result<ItemWrapper<TVShow>>
    suspend fun latestTvShows(page: Int = 1): Result<ItemWrapper<TVShow>>
    suspend fun searchTvShows(page: Int = 1, query: String): Result<ItemWrapper<TVShow>>
    suspend fun tvShowTrailers(tvId: Int = 1): Result<VideoWrapper>
    suspend fun tvShowCredits(tvId: Int): Result<CreditWrapper>

    // Person:
    suspend fun getPerson(personId: Int): Result<Person>
}