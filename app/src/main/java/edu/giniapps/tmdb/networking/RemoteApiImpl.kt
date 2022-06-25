package edu.giniapps.tmdb.networking

import android.util.Log
import edu.giniapps.tmdb.models.TmdbInternalThrowable
import edu.giniapps.tmdb.models.TmdbNetworkThrowable
import java.io.IOException
import javax.inject.Inject

private const val TAG = "RemoteApiImpl"

class RemoteApiImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val genreApi: GenreApi,
    /*private val tvApi: TvApi,
    private val personApi: PersonApi*/
) : RemoteApi {

    override suspend fun popularMovies(page: Int) =
        performCatching { movieApi.popularMovies(page).items }

    override suspend fun nowPlayingMovies(page: Int) =
        performCatching { movieApi.nowPlayingMovies(page).items }

    /*override suspend fun topRatedMovies(page: Int) =
        try {
            Success(movieApi.topRatedMovies(page))
        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun upcomingMovies(page: Int) =
        try {
            Success(movieApi.upcomingMovies(page))
        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun latestMovie() =
        try {
            Success(movieApi.latestMovie())


        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun getMovieById(id: Int) =
        try {
            Success(movieApi.getMovieById(id))
        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun searchMovies(page: Int, query: String) =
        try {
            Success(movieApi.searchMovies(page, query))
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
        }*/

    override suspend fun genres() =
        performCatching { genreApi.genres().genres }

    /*override suspend fun popularTvShows(page: Int) =
        try {
            Success(tvApi.popularTvShows(page))
        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun topRatedTvShows(page: Int) =
        try {
            Success(tvApi.topRatedTvShows(page))
        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun runningTvShows(page: Int) =
        try {
            Success(tvApi.runningTvShows(page))
        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun searchTvShows(page: Int, query: String) =
        try {
            Success(tvApi.searchTvShows(page, query))
        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun tvShowTrailers(tvId: Int) =
        try {
            Success(tvApi.tvShowTrailers(tvId))
        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun tvShowCredits(tvId: Int) =
        try {
            Success(tvApi.tvShowCredits(tvId))
        } catch (t: Throwable) {
            Failure(t)
        }

    override suspend fun getPerson(personId: Int) =
        try {
            Success(personApi.getPerson(personId))
        } catch (t: Throwable) {
            Failure(t)
        }*/
}

private suspend fun <T : Any> performCatching(block: suspend () -> T) =
    try {
        Result.success(block())
    } catch (t: Throwable) {
        // Todo: Change to logger
        Log.e(TAG, t.message.toString())

        if (t is IOException)
            Result.failure(TmdbNetworkThrowable)
        else
            Result.failure(TmdbInternalThrowable)
    }