package edu.giniapps.day23.networking

import edu.giniapps.day23.models.response.CreditWrapper
import edu.giniapps.day23.models.response.ItemWrapper
import edu.giniapps.day23.models.response.TVShow
import edu.giniapps.day23.models.response.VideoWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvApi {

    @GET("3/tv/popular?language=en")
    suspend fun popularItems(@Query("page") page: Int = 1): ItemWrapper<TVShow>

    @GET("3/tv/top_rated?language=en")
    suspend fun topRatedItems(@Query("page") page: Int = 1): ItemWrapper<TVShow>

    @GET("3/tv/on_the_air?language=en")
    suspend fun latestItems(@Query("page") page: Int = 1): ItemWrapper<TVShow>

    @GET("3/search/tv?language=en")
    suspend fun searchItems(
        @Query("page") page: Int = 1,
        @Query("query") query: String
    ): ItemWrapper<TVShow>

    @GET("3/tv/{tvId}/videos")
    suspend fun tvTrailers(@Path("tvId") tvId: Int): VideoWrapper

    @GET("3/tv/{tvId}/credits")
    suspend fun tvCredit(@Path("tvId") tvId: Int): CreditWrapper
}
