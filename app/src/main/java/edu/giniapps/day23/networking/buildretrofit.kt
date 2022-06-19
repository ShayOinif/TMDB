package edu.giniapps.day23.networking

import edu.giniapps.day23.BuildConfig.TMDB_API_KEY
import edu.giniapps.day23.BuildConfig.TMDB_BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY = "api_key"

fun buildAuthorizationInterceptor() = object : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (TMDB_API_KEY.isBlank())
            return chain.proceed(originalRequest)

        val newRequest = originalRequest.newBuilder()
            .url(originalRequest.url.newBuilder().addQueryParameter(API_KEY, TMDB_API_KEY).build())
            .build()

        return chain.proceed(newRequest)
    }
}

fun buildHttpClient() =
    OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(buildAuthorizationInterceptor()).build()

fun buildRetrofit(): Retrofit =
    Retrofit.Builder().baseUrl(TMDB_BASE_URL).addConverterFactory(GsonConverterFactory.create())
        .client(
            buildHttpClient()
        ).build()

fun buildMovieService(): MovieApi =
    buildRetrofit().create(MovieApi::class.java)

fun buildTvShowsService(): TvApi =
    buildRetrofit().create(TvApi::class.java)

fun buildPersonService(): PersonApi =
    buildRetrofit().create(PersonApi::class.java)

fun buildRemoteApi(): RemoteApi =
    RemoteApiImpl(
        buildMovieService(),
        buildTvShowsService(),
        buildPersonService()
    )