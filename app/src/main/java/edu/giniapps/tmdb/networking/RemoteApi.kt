package edu.giniapps.tmdb.networking

import edu.giniapps.tmdb.models.response.Genre
import edu.giniapps.tmdb.models.response.Movie

interface RemoteApi {
    // Movies:
    suspend fun popularMovies(page: Int = 1): Result<List<Movie>>
    suspend fun nowPlayingMovies(page: Int = 1): Result<List<Movie>>
    /*suspend fun topRatedMovies(page: Int = 1): edu.giniapps.day23.models.Result<List<Movie>>
    suspend fun upcomingMovies(page: Int = 1): edu.giniapps.day23.models.Result<List<Movie>>
    suspend fun getMovieById(id: Int): edu.giniapps.day23.models.Result<Movie>
    suspend fun searchMovies(page: Int = 1, query: String): edu.giniapps.day23.models.Result<List<Movie>>
    suspend fun movieTrailers(movieId: Int = 1): edu.giniapps.day23.models.Result<List<Video>>
    suspend fun movieCredits(movieId: Int): edu.giniapps.day23.models.Result<List<Credit>>*/

    // Genres:
    suspend fun genres(): Result<List<Genre>>

    // TV:
    /*suspend fun popularTvShows(page: Int = 1): edu.giniapps.day23.models.Result<TVShow>
    suspend fun topRatedTvShows(page: Int = 1): edu.giniapps.day23.models.Result<TVShow>
    suspend fun runningTvShows(page: Int = 1): edu.giniapps.day23.models.Result<TVShow>
    suspend fun searchTvShows(page: Int = 1, query: String): edu.giniapps.day23.models.Result<TVShow>
    suspend fun tvShowTrailers(tvId: Int = 1): edu.giniapps.day23.models.Result<Video>
    suspend fun tvShowCredits(tvId: Int): edu.giniapps.day23.models.Result<Credit>

    // Person:
    suspend fun getPerson(personId: Int): edu.giniapps.day23.models.Result<Person>*/
}