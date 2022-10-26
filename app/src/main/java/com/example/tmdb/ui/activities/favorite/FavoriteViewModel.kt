package com.example.tmdb.ui.activities.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.domain.dataService.FavoriteService
import com.example.tmdb.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val favoriteService: FavoriteService) : ViewModel() {
    val favoriteMoviesViewState: MutableLiveData<List<Movie>> = MutableLiveData(ArrayList())
    val errorViewState: MutableLiveData<String> = MutableLiveData()
    val isResultFound: MutableLiveData<Boolean> = MutableLiveData(true)

    init {
        getFavoriteMovies()
    }

    private fun getFavoriteMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val resource = favoriteService.getFavoriteMovies()
            if (resource.data != null) {
                isResultFound.postValue(true)
                favoriteMoviesViewState.postValue(resource.data as List<Movie>?)
            } else {
                isResultFound.postValue(false)
                errorViewState.postValue(resource.error)
            }
        }
    }
}