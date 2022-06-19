package edu.giniapps.day23.networking

import edu.giniapps.day23.models.response.Failure
import edu.giniapps.day23.models.response.Success

class RemoteApiImpl(
    private val movieApi: MovieApi,
    private val tvApi: TvApi,
    private val personApi: PersonApi
) : RemoteApi {

    override suspend fun popularMovies(page: Int) =
        try {
            Success(movieApi.popularItems(page))
        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun topRatedMovies(page: Int) =
        try {
            Success(movieApi.topRatedItems(page))
        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun latestMovies(page: Int) =
        try {
            Success(movieApi.latestItems(page))
        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun searchMovies(page: Int, query: String) =
        try {
            Success(movieApi.searchItems(page, query))
        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun movieTrailers(movieId: Int) =
        try {
            Success(movieApi.movieTrailers(movieId))
        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun movieCredits(movieId: Int) =
        try {
            Success(movieApi.movieCredit(movieId))
        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun popularTvShows(page: Int) =
        try {
            Success(tvApi.popularItems(page))
        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun topRatedTvShows(page: Int) =
        try {
            Success(tvApi.topRatedItems(page))
        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun latestTvShows(page: Int) =
        try {
            Success(tvApi.latestItems(page))
        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun searchTvShows(page: Int, query: String) =
        try {
            Success(tvApi.searchItems(page, query))
        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun tvShowTrailers(tvId: Int) =
        try {
            Success(tvApi.tvTrailers(tvId))
        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun tvShowCredits(tvId: Int) =
        try {
            Success(tvApi.tvCredit(tvId))
        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun getPerson(personId: Int) =
        try {
            Success(personApi.getPerson(personId))
        } catch (t: Throwable) {
            Failure(t)
        }
}