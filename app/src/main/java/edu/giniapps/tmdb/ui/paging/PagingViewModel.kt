package edu.giniapps.tmdb.ui.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.giniapps.tmdb.networking.MovieApi
import edu.giniapps.tmdb.networking.PagingDataSource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PagingViewModel @Inject constructor(private val movieApi: MovieApi): ViewModel() {
    private val popularMovies =
        Pager(config = PagingConfig(pageSize = 20), pagingSourceFactory = {
            PagingDataSource(movieApi)
        }).flow.cachedIn(viewModelScope)

    val adapter = PagingAdapter().apply {
        withLoadStateHeaderAndFooter(
            header = PagingLoadStateAdapter { this.retry() },
            footer = PagingLoadStateAdapter { this.retry() }
        )
    }

    fun start() {
        viewModelScope.launch {
            popularMovies.collectLatest { pagedData ->
                adapter.submitData(pagedData)
            }
        }
    }
}