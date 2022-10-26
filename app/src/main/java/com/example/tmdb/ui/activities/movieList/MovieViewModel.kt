package com.example.tmdb.ui.activities.movieList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.domain.dataService.FavoriteService
import com.example.tmdb.domain.dataService.MovieService
import com.example.tmdb.domain.model.Movie
import com.example.tmdb.domain.model.MovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieService: MovieService,
                                         private val favoriteService: FavoriteService) : ViewModel() {
    var page = 1
    val movieViewStateData: MutableLiveData<MovieResponse> = MutableLiveData()
    val errorViewStateData: MutableLiveData<String> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isResultFound: MutableLiveData<Boolean> = MutableLiveData(true)
    private lateinit var searchJob : Job

    fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val resource = movieService.getMovies(page)
            if (resource.data != null) {
                isLoading.postValue(false)
                val movieResponse = resource.data as MovieResponse
                isResultFound.postValue(movieResponse.results.isEmpty().not() || page > 1)
                movieViewStateData.postValue(movieResponse)
            } else {
                isLoading.postValue(false)
                isResultFound.postValue(false)
                errorViewStateData.postValue(resource.error)
            }

        }
    }

    fun loadMoreMovies() {
        page++
        getMovies()
    }

    fun getSearchMovieResult(query: String) {
        if(this::searchJob.isInitialized){
            searchJob.cancel(null)
        }
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            val resource = if (query.isEmpty()) {
                movieService.getMovies(page)
            } else {
                movieService.getSearchMovieResult(query, page)
            }
            isLoading.postValue(false)
            if (resource.data != null) {
                movieViewStateData.postValue(resource.data as MovieResponse)
            } else {
                isResultFound.postValue(false)
                errorViewStateData.postValue(resource.error)
            }
        }
    }

    fun loadMoreWithSearch(query: String) {
        page++
        getSearchMovieResult(query)
    }

    fun addToFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteService.addToFavorite(movie)
        }
    }


}