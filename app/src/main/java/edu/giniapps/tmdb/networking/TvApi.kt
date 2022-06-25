package edu.giniapps.tmdb.networking

import edu.giniapps.tmdb.models.response.ItemWrapper
import edu.giniapps.tmdb.models.response.TVShow
import edu.giniapps.tmdb.models.response.VideoWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvApi {

    @GET("3/tv/popular?language=en")
    suspend fun popularTvShows(@Query("page") page: Int = 1): ItemWrapper<TVShow>

    @GET("3/tv/top_rated?language=en")
    suspend fun topRatedTvShows(@Query("page") page: Int = 1): ItemWrapper<TVShow>

    @GET("3/tv/on_the_air?language=en")
    suspend fun runningTvShows(@Query("page") page: Int = 1): ItemWrapper<TVShow>

    @GET("3/search/tv?language=en")
    suspend fun searchTvShows(
        @Query("page") page: Int = 1,
        @Query("query") query: String
    ): ItemWrapper<TVShow>

    @GET("3/tv/{tvId}/videos")
    suspend fun tvShowTrailers(@Path("tvId") tvId: Int): VideoWrapper

    /*@GET("3/tv/{tvId}/credits")
    suspend fun tvShowCredits(@Path("tvId") tvId: Int): CreditWrapper*/
}
