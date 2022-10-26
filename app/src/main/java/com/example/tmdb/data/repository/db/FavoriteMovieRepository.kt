package com.example.tmdb.data.repository.db

import com.example.tmdb.data.database.Database
import com.example.tmdb.domain.model.Movie
import com.example.tmdb.domain.repository.db.IFavoriteMovieRepository
import javax.inject.Inject

class FavoriteMovieRepository @Inject constructor(val database: Database) :
    IFavoriteMovieRepository {

    override suspend fun insertFavoriteMovie(favoriteMovie: Movie) {
        database.favoriteMovieDAO().insertIntoFavorite(favoriteMovie)
    }

    override suspend fun isFavoriteExist(movieId: Int): Boolean {
        return database.favoriteMovieDAO().isFavoriteExist(movieId) != null
    }

    override suspend fun getFavoriteMovies(): List<Movie>? {
        return database.favoriteMovieDAO().getFavoriteMovies()
    }
}