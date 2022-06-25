package edu.giniapps.tmdb.networking

import edu.giniapps.tmdb.models.response.GenreWrapper
import retrofit2.http.GET

interface GenreApi {
    @GET("3/genre/movie/list")
    suspend fun genres(): GenreWrapper
}