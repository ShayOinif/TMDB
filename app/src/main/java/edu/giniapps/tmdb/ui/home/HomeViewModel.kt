package edu.giniapps.tmdb.ui.home

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.giniapps.tmdb.R
import edu.giniapps.tmdb.models.*
import edu.giniapps.tmdb.repository.Repository
import edu.giniapps.tmdb.ui.home.HomeViewModel.MovieClickCallback
import edu.giniapps.tmdb.ui.home.adapters.TMDBCategoryAdapter
import edu.giniapps.tmdb.ui.home.adapters.TMDBItemAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

// Todo: Check shared adapter for recycler view

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _exceptionAction = MutableLiveData<ExceptionAction>()
    val exceptionAction: LiveData<ExceptionAction>
        get() = _exceptionAction

    private val _refreshing = MutableLiveData<Boolean>()
    val refreshing: LiveData<Boolean>
        get() = _refreshing

    val viewPool = RecyclerView.RecycledViewPool()
    val categoriesAdapter = TMDBCategoryAdapter(WeakReference(viewPool))

    private val _navigate = MutableLiveData<MovieWithGenres?>(null)
    val navigate
        get() = _navigate

    private val movieClickCallback = MovieClickCallback { movie ->
        _navigate.postValue(
            movie
        )
    }

    private val categoriesFlowsAndAdapters = listOf(
        CategoriesFlowsAndAdapters(
            repository.popularMovies,
            R.string.popular_movies_category,
            TMDBItemAdapter(WeakReference(movieClickCallback)),
        ),
        CategoriesFlowsAndAdapters(
            repository.nowPlayingMovies,
            R.string.now_playing_movies_category,
            TMDBItemAdapter(WeakReference(movieClickCallback)),
        )
    )

    private val categoriesRefreshers = listOf(
        repository::refreshPopularMovies,
        repository::refreshPopularMovies
    )

    // Todo: Put in function
    init {
        if (!repository.initialized) {
            _refreshing.value = true

            viewModelScope.launch {
                repository.init().onFailure { t ->
                    t.matchExceptionToAction()
                }.onSuccess {
                    showMovies()
                }

                _refreshing.value = false
            }
        }
    }

    fun refreshMovies() {
        _refreshing.value?.let { refreshing ->
            if (!refreshing) {
                _refreshing.value = true

                viewModelScope.launch {
                    categoriesRefreshers.forEach { refresher ->
                        refresher().onFailure { t ->
                            t.matchExceptionToAction()

                            return@forEach
                        }
                    }

                    _refreshing.value = false
                }
            }
        }
    }

    fun acknowledgeNavigation() {
        _navigate.value = null
    }

    private fun showMovies() {

        val movieCategoryItemList = mutableListOf<MoviesCategoryItem>()

        categoriesFlowsAndAdapters.forEach { category ->

            movieCategoryItemList.add(
                MoviesCategoryItem(category.categoryNameStringRes, category.categoryAdapter)
            )

            viewModelScope.launch {
                category.categoryFlow.collect { moviesResult ->
                    moviesResult.onSuccess { moviesList ->
                        category.categoryAdapter.submitList(moviesList)
                    }.onFailure { t ->
                        t.matchExceptionToAction()
                    }
                }
            }
        }

        categoriesAdapter.submitList(movieCategoryItemList)
    }

    private fun reignite() {
        _refreshing.value = true

        viewModelScope.launch {
            repository.reignite().onFailure { t ->
                t.matchExceptionToAction()
            }.onSuccess {
                showMovies()
            }

            _refreshing.value = false
        }
    }

    private fun Throwable.matchExceptionToAction() {
        _exceptionAction.value = when (this) {
            is TmdbNetworkThrowable ->
                ExceptionAction(
                    R.string.network_error_message,
                    R.string.try_again_message,
                    ::refreshMovies
                )
            is TmdbServerThrowable ->
                ExceptionAction(
                    R.string.server_error_message,
                    null,
                    {}
                )
            is TmdbBrokenThrowable ->
                ExceptionAction(
                    R.string.app_broken_message,
                    R.string.contact_support_message,
                    {},
                    Snackbar.LENGTH_INDEFINITE
                )
            else ->
                ExceptionAction(
                    R.string.internal_error_message,
                    R.string.reload_all_message,
                    ::reignite
                )
        }
    }

    fun interface MovieClickCallback {
        fun onMovieClicked(movie: MovieWithGenres)
    }

    private data class CategoriesFlowsAndAdapters<T>(
        val categoryFlow: Flow<T>,
        @StringRes
        val categoryNameStringRes: Int,
        val categoryAdapter: TMDBItemAdapter
    )
}

class ExceptionAction(
    @StringRes
    val messageRes: Int,
    @StringRes
    val actionRes: Int?,
    val action: (() -> Unit),
    val length: Int = Snackbar.LENGTH_LONG
)
