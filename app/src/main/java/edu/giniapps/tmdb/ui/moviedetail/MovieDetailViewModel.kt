package edu.giniapps.tmdb.ui.moviedetail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(): ViewModel() {
/*
    @Inject
    lateinit var networkStatusChecker: NetworkStatusChecker

    @Inject
    lateinit var favoriteMoviesDatasource: DatasourceImpl

    private var _movie: Movie? = null
    private val movie
        get() = _movie!!

    val thumbnail
        get() = POSTER_BASE_URL + movie.posterPath

    val title
        get() = movie.name

    val favorite: Flow<Int> = flow { emit(R.drawable.ic_baseline_star_24)
        *//* _movie?.let {
            favoriteMoviesDatasource.getFavorite(it.id).collect { isFavorite ->
                if (isFavorite)
                    emit(R.drawable.ic_star_selected)
                else
                    emit(R.drawable.ic_baseline_star_24)
            }
            } ?: emit(R.drawable.ic_baseline_star_24)*//*
       }


    *//*= flow {
        while(true) {
            _movie?.let {
                val latestNews = favoriteMoviesDatasource.getFavorite(it.id)

                val result = if (latestNews)
                    R.drawable.ic_star_selected
                else
                    R.drawable.ic_baseline_star_24

                emit(result) // Emits the result of the request to the flow
                delay(500)
            }
        }
    }*//*

    private var _trailer = ""
    val trailer
        get() = _trailer

    val year
        get() = movie.releaseDate

    val overview
        get() = movie.overview

    val voteAverage
        get() = (movie.voteAverage / RATING_EVALUATION_DENOMINATOR).toFloat()


    private val _isReady = MutableLiveData(false)
    val isReady
        get() = _isReady

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    fun setupMovie(movie: Movie) {
        this._movie = movie
    }

    fun loadMovie() {
        *//*launchDataLoad {
            if (!networkStatusChecker.performIfConnectedToInternet {

                    when (val result = AppManager.remoteApi.movieTrailers(movie.id)) {
                        is Success -> {
                            _trailer = result.data.videos[0].videoId

                            _isReady.value = true
                        }
                        is Failure -> throw result.exc ?: Throwable("Unknown error")
                    }
                })
                throw Throwable("No internet connection!")
        }*//*
    }

    fun changeFavorite() {
        *//*_movie?.let {

            it.favorite = !it.favorite

            viewModelScope.launch {
                try {
                    favoriteMoviesDatasource.insertAll(listOf(it))
                } catch (e: Exception) {
                    Log.d("Shay", e.message.toString())
                }
            }
        }*//*
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _loading.value = true
                _errorMessage.value = null
                _isReady.value = false

                block()
            } catch (error: Throwable) {
                Log.d("Shay", error.message.toString())
                _errorMessage.value = error.message.toString()
                _isReady.value = false
            } finally {
                _loading.value = false
            }
        }
    }*/
}


