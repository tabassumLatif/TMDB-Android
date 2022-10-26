package com.example.tmdb.domain.repository.db

import com.example.tmdb.data.model.MovieDTO

interface IMoviesRepository {
    suspend fun getMoviesFromDB(): List<MovieDTO>

    suspend fun insertMovies(movies: ArrayList<MovieDTO>)

    suspend fun searchMovieResultFromDB(query: String): List<MovieDTO>?
}