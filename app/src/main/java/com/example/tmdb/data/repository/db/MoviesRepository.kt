package com.example.tmdb.data.repository.db

import com.example.tmdb.data.database.Database
import com.example.tmdb.data.model.MovieDTO
import com.example.tmdb.domain.repository.db.IMoviesRepository
import javax.inject.Inject

class MoviesRepository @Inject constructor(val database: Database) : IMoviesRepository {
    override suspend fun getMoviesFromDB(): List<MovieDTO> {
        return database.movieDAO().getMovies()
    }

    override suspend fun insertMovies(movies: ArrayList<MovieDTO>) {
        database.movieDAO().insertAll(movies)
    }

    override suspend fun searchMovieResultFromDB(query: String): List<MovieDTO> {
        return database.movieDAO().getMoviesByQuery(query)
    }
}