package edu.giniapps.tmdb.repository

import android.util.Log
import edu.giniapps.tmdb.TmdbApp
import edu.giniapps.tmdb.datasource.DataSource
import edu.giniapps.tmdb.models.TmdbBrokenThrowable
import edu.giniapps.tmdb.models.TmdbInternalThrowable
import edu.giniapps.tmdb.models.TmdbNotInitialized
import edu.giniapps.tmdb.models.response.Movie.Companion.NOW_PLAYING
import edu.giniapps.tmdb.models.response.Movie.Companion.POPULAR
import edu.giniapps.tmdb.networking.NetworkStatusChecker
import edu.giniapps.tmdb.networking.RemoteApi
import edu.giniapps.tmdb.networking.performIfConnectedToInternet
import java.text.SimpleDateFormat
import javax.inject.Inject
import javax.inject.Singleton

private const val TIMESTAMP_FORMAT = "yyyyMMdd"
private const val DEFAULT_VALUE_FOR_UPDATE_TIMESTAMP = "00000000"

// TODO: Add logging

@Singleton
class RepositoryImpl @Inject constructor(
    private val remoteApi: RemoteApi,
    private val dataSource: DataSource,
    private val networkStatusChecker: NetworkStatusChecker,
) : Repository {

    private var _initialized = false
    override val initialized
        get() = _initialized

    override val popularMovies = dataSource.popularMovies

    override val nowPlayingMovies = dataSource.nowPlayingMovies

    override suspend fun init(): Result<Unit> {
        if (!_initialized) {
            // TODO: Break to functions

            // Check if the genres table has been created through SP
            if (!TmdbApp.SHARED_PREFERENCES.getBoolean(TmdbApp.DB_HAS_GENRES_KEY, false)) {
                refreshGenres().onFailure { t ->
                    return Result.failure(t)
                }

                TmdbApp.SHARED_PREFERENCES.edit()
                    .putBoolean(TmdbApp.DB_HAS_GENRES_KEY, true).commit()
            }

            // Check when the movies where last updated through SP
            val currentDate = SimpleDateFormat(TIMESTAMP_FORMAT).format(System.currentTimeMillis())

            val lastUpdateDate = TmdbApp.SHARED_PREFERENCES.getString(
                TmdbApp.DB_LAST_UPDATE,
                DEFAULT_VALUE_FOR_UPDATE_TIMESTAMP
            )!!

            if (currentDate > lastUpdateDate) {
                refreshAllMovies().onFailure { t ->
                    return Result.failure(t)
                }

                TmdbApp.SHARED_PREFERENCES.edit()
                    .putString(TmdbApp.DB_LAST_UPDATE, currentDate).commit()
            }

            _initialized = true

        }

        return Result.success(Unit)
    }

    override suspend fun reignite(): Result<Unit> {
        _initialized = false

        TmdbApp.SHARED_PREFERENCES.edit()
            .putBoolean(TmdbApp.DB_HAS_GENRES_KEY, false)
            .putString(TmdbApp.DB_LAST_UPDATE, DEFAULT_VALUE_FOR_UPDATE_TIMESTAMP).commit()

        return init().onFailure { t ->
            if (t is TmdbInternalThrowable)
                return Result.failure(TmdbBrokenThrowable)
        }
    }

    // Refreshing from api anyhow, in real world situation check if the data is in the DB
    override suspend fun refreshPopularMovies() =
        if (_initialized) {
            refreshPopularMoviesPrivate()
        } else
            Result.failure(TmdbNotInitialized)

    private suspend fun refreshPopularMoviesPrivate() =
        insertToDataSource(
            { dataSource.deletePopularMovies() },
            { remoteApi.popularMovies(1) },
            { popularMovies ->
                dataSource.insertMovies(popularMovies.map { movie ->
                    movie.category = POPULAR

                    movie
                })
            }
        )

    override suspend fun refreshNowPlayingMovies() =
        if (_initialized) {
            refreshNowPlayingMoviesPrivate()
        } else
            Result.failure(TmdbNotInitialized)

    private suspend fun refreshNowPlayingMoviesPrivate() =
        insertToDataSource(
            { dataSource.deleteNowPlayingMovies() },
            { remoteApi.nowPlayingMovies(1) },
            { nowPlayingMovies ->
                dataSource.insertMovies(nowPlayingMovies.map { movie ->
                    movie.category = NOW_PLAYING

                    movie
                })
            }
        )

    private suspend fun refreshGenres() =
        insertToDataSource(
            { dataSource.deleteGenres() },
            { remoteApi.genres() },
            { genres ->
                dataSource.insertGenres(genres)
            }
        )

    private suspend fun refreshAllMovies() =
        refreshPopularMoviesPrivate().onSuccess {
            return refreshNowPlayingMoviesPrivate()
        }

    private suspend fun <T : Any> insertToDataSource(
        localDelete: suspend () -> Result<Unit>,
        remoteBlock: suspend () -> Result<T>,
        localBlock: suspend (T) -> Result<Unit>
    ) =
        networkStatusChecker.performIfConnectedToInternet {
            localDelete().onSuccess {
                remoteBlock().onSuccess { result ->
                    return@performIfConnectedToInternet localBlock(result).onFailure {
                        Log.d("Shay", it.message.toString())
                    }
                }.onFailure {
                    Log.d("Shay", it.message.toString())
                }
                return@performIfConnectedToInternet Result.failure(TmdbInternalThrowable)
            }.onFailure {
                Log.d("Shay", it.message.toString())
            }
        }
}