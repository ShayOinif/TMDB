package edu.giniapps.tmdb.networking

import edu.giniapps.tmdb.models.response.ItemWrapper
import edu.giniapps.tmdb.models.response.Movie
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("3/movie/popular")
    suspend fun popularMovies(
        @Query("page") page: Int = 1
    ): ItemWrapper<Movie>

    @GET("3/movie/now_playing")
    suspend fun nowPlayingMovies(
        @Query("page") page: Int = 1
    ): ItemWrapper<Movie>
    /*@GET("3/discover/movie?vote_count.gte=500&language=en&sort_by=vote_average.desc")
    suspend fun topRatedMovies(@Query("page") page: Int = 1): ItemWrapper<Movie>

    @GET("3/movie/upcoming?language=en")
    suspend fun upcomingMovies(@Query("page") page: Int = 1): ItemWrapper<Movie>

    @GET("3/movie/{id}")
    suspend fun getMovieById(@Path("id") id: Int): Movie

    @GET("3/search/movie?language=en")
    suspend fun searchMovies(
        @Query("page") page: Int = 1,
        @Query("query") query: String
    ): ItemWrapper<Movie>

    @GET("3/movie/{movieId}/videos")
    suspend fun movieTrailers(@Path("movieId") movieId: Int): VideoWrapper

    @GET("3/movie/{movieId}/credits")
    suspend fun movieCredit(@Path("movieId") movieId: Int): CreditWrapper*/
}
