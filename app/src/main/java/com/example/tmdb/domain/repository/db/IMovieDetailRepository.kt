package com.example.tmdb.domain.repository.db

import com.example.tmdb.data.model.MovieDetailDTO

interface IMovieDetailRepository {

    suspend fun getMovieDetailFromDB(moveId: Int): MovieDetailDTO?

    suspend fun insertMovieDetail(movieDetail: MovieDetailDTO)

}