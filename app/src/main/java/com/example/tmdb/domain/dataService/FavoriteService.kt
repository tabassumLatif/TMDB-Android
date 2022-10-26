package com.example.tmdb.domain.dataService

import com.example.tmdb.domain.utils.Resource
import com.example.tmdb.domain.model.Movie
import com.example.tmdb.domain.repository.db.IFavoriteMovieRepository
import com.example.tmdb.domain.utils.Error
import javax.inject.Inject

class FavoriteService @Inject constructor(
    private val dbFavoriteMovieRepository: IFavoriteMovieRepository,
) {

    suspend fun addToFavorite(movie: Movie) {
        dbFavoriteMovieRepository.insertFavoriteMovie(movie)
    }

    suspend fun getFavoriteMovies(): Resource {
        val favoriteMovies = dbFavoriteMovieRepository.getFavoriteMovies()
        return if (favoriteMovies.isNullOrEmpty().not()) {
            Resource.Success(favoriteMovies)
        } else {
            Resource.Error(Error.FAVORITE_NOT_FOUND.message)
        }
    }

}