package com.example.tmdb.ui.activities.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.domain.dataService.MovieDetailService
import com.example.tmdb.domain.model.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieDetailService: MovieDetailService) :
    ViewModel() {
    var movieViewStateData: MutableLiveData<MovieDetail> = MutableLiveData()
    var errorViewStateData: MutableLiveData<String> = MutableLiveData()

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val resource = movieDetailService.getMovieDetail(movieId)
            if (resource.data != null) {
                movieViewStateData.postValue(resource.data as MovieDetail)
            } else {
                errorViewStateData.postValue(resource.error)
            }

        }
    }

}