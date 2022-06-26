package edu.giniapps.tmdb.networking

import androidx.paging.PagingSource
import androidx.paging.PagingState
import edu.giniapps.tmdb.models.response.Movie

class PagingDataSource (private val movieApi: MovieApi): PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, Movie> {
        return try {

            val nextPageNumber = params.key ?: 1
            val response = movieApi.popularMovies(nextPageNumber)
            PagingSource.LoadResult.Page(
                data = response.items,
                prevKey = if (nextPageNumber > 1) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < response.pages) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            PagingSource.LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }

}