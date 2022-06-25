package edu.giniapps.tmdb.datasource

import edu.giniapps.tmdb.db.GenreDao
import edu.giniapps.tmdb.db.MovieDao
import edu.giniapps.tmdb.db.MovieGenreCrossRefDao
import edu.giniapps.tmdb.models.TmdbInternalThrowable
import edu.giniapps.tmdb.models.response.Genre
import edu.giniapps.tmdb.models.response.Movie
import edu.giniapps.tmdb.models.response.Movie.Companion.NOW_PLAYING
import edu.giniapps.tmdb.models.response.Movie.Companion.POPULAR
import edu.giniapps.tmdb.models.response.MovieGenre
import edu.giniapps.tmdb.utils.TmdbLogger
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val TAG = "DatasourceImpl"

class DatasourceImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val genreDao: GenreDao,
    private val movieGenreCrossRefDao: MovieGenreCrossRefDao,
    private val tmdbLogger: TmdbLogger
) : DataSource {

    override val popularMovies = movieDao.getPopularMovies().map {
        Result.success(it)
    }.catch { t ->
        currentCoroutineContext().cancel(null)
        emit(catchInFlow(t))
    }

    override val nowPlayingMovies = movieDao.getNowPlayingMovies().map {
        Result.success(it)
    }.catch { t ->
        currentCoroutineContext().cancel(null)
        emit(catchInFlow(t))
    }

    override suspend fun insertMovies(movies: List<Movie>) =
        performCatching {
            movieDao.insertAll(movies)

            movies.forEach { movie ->
                movie.genreIds.map { genreId ->
                    MovieGenre(movie.id, genreId)
                }.also { movieGenreCrossRefDao.insertAll(it) }
            }
        }

    override suspend fun insertGenres(genres: List<Genre>) =
        performCatching {
            genreDao.insertAll(genres)
        }

    override suspend fun deletePopularMovies() =
        performCatching {
            movieDao.deleteByCategory(POPULAR)
        }

    override suspend fun deleteNowPlayingMovies() =
        performCatching {
            movieDao.deleteByCategory(NOW_PLAYING)
        }

    override suspend fun deleteGenres() =
        performCatching {
            genreDao.deleteAll()
        }

    private suspend fun performCatching(block: suspend () -> Unit) =
        try {
            block()
            Result.success(Unit)
        } catch (t: Throwable) {
            catchInFlow(t)
        }

    private fun <T> catchInFlow(t: Throwable): Result<T> {
        tmdbLogger.logError(TAG, t.message.toString())
        return Result.failure(TmdbInternalThrowable)
    }
}