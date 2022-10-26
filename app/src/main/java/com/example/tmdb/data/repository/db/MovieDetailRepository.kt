package com.example.tmdb.data.repository.db

import com.example.tmdb.data.database.Database
import com.example.tmdb.data.model.MovieDetailDTO
import com.example.tmdb.domain.repository.db.IMovieDetailRepository
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(val database: Database) : IMovieDetailRepository {

    override suspend fun getMovieDetailFromDB(moveId: Int): MovieDetailDTO {
        return database.movieDetailDAO().getDetail(moveId)
    }
    override suspend fun insertMovieDetail(movieDetail: MovieDetailDTO) {
        database.movieDetailDAO().insertMovieDetail(movieDetail)
    }
}