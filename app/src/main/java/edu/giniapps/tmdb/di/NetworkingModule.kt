package edu.giniapps.tmdb.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.giniapps.tmdb.BuildConfig
import edu.giniapps.tmdb.networking.GenreApi
import edu.giniapps.tmdb.networking.MovieApi
import edu.giniapps.tmdb.networking.PersonApi
import edu.giniapps.tmdb.networking.TvApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY_NAME = "api_key"
private const val TMDB_BASE_URL = "https://api.themoviedb.org/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Provides
    fun providesGsonFactory(): Converter.Factory = GsonConverterFactory.create()

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideAuthorizationInterceptor() = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()

            if (BuildConfig.TMDB_API_KEY.isBlank())
                return chain.proceed(originalRequest)

            val newRequest = originalRequest.newBuilder()
                .url(
                    originalRequest.url.newBuilder().addQueryParameter(
                        API_KEY_NAME,
                        BuildConfig.TMDB_API_KEY
                    ).build()
                )
                .build()

            return chain.proceed(newRequest)
        }
    }


    @Provides
    fun provideOkHttpClient(
        authInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ) =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor).build()

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: Converter.Factory
    ): Retrofit =
        Retrofit.Builder().baseUrl(TMDB_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieApi =
        retrofit.create(MovieApi::class.java)

    @Provides
    fun provideTvShowsService(retrofit: Retrofit): TvApi =
        retrofit.create(TvApi::class.java)

    @Provides
    fun providePersonService(retrofit: Retrofit): PersonApi =
        retrofit.create(PersonApi::class.java)

    @Provides
    fun provideGenreService(retrofit: Retrofit): GenreApi =
        retrofit.create(GenreApi::class.java)
}