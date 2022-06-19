package edu.giniapps.day23.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.giniapps.day23.AppManager
import edu.giniapps.day23.models.response.Failure
import edu.giniapps.day23.models.response.Movie
import edu.giniapps.day23.models.response.Success
import edu.giniapps.day23.ui.adapters.TMDBItemAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    val adapter = TMDBItemAdapter()

    fun loadMovies() {
        launchDataLoad {
            val result = AppManager.remoteApi.popularMovies()

            _movies.value = when (result) {
                is Success -> result.data.items
                is Failure -> throw result.exc ?: Throwable("Unknown error")
            }

            _movies.value?.let { movieList ->
                adapter.submitList(movieList)
            } ?: run {
                _errorMessage.value = "Problem fetching the movies"
            }
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _loading.value = true
                _errorMessage.value = null

                block()
            } catch (error: Throwable) {
                Log.d("Shay", error.message.toString())
                _errorMessage.value = error.message.toString()
            } finally {
                _loading.value = false
            }
        }
    }

}