package com.example.tmdb.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tmdb.domain.model.Movie

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM Movie")
    suspend fun getFavoriteMovies(): List<Movie>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIntoFavorite(favoriteMovie: Movie)

    @Query("Select * from Movie Where id = :movieId")
    suspend fun isFavoriteExist(movieId: Int): Movie?

}
