package edu.giniapps.day23.networking

import edu.giniapps.day23.models.response.Person
import retrofit2.http.GET
import retrofit2.http.Path

interface PersonApi {
    @GET("3/person/{personId}")
    suspend fun getPerson(@Path("personId") personId: Any): Person
}