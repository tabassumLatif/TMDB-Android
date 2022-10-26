package com.example.tmdb.domain.repository.db

import com.example.tmdb.domain.model.Movie

interface IFavoriteMovieRepository {
    suspend fun insertFavoriteMovie(favoriteMovie: Movie)

    suspend fun getFavoriteMovies(): List<Movie>?

    suspend fun isFavoriteExist(movieId: Int): Boolean
}