package com.example.tmdb.data.database

import androidx.room.RoomDatabase
import com.example.tmdb.data.database.dao.FavoriteDao
import com.example.tmdb.data.database.dao.MovieDao
import com.example.tmdb.data.database.dao.MovieDetailDao
import com.example.tmdb.data.model.MovieDTO
import com.example.tmdb.data.model.MovieDetailDTO
import com.example.tmdb.domain.model.Movie

@androidx.room.TypeConverters(value = [TypeConverters::class])
@androidx.room.Database(
    entities = [MovieDetailDTO::class, MovieDTO::class, Movie::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {

    abstract fun movieDetailDAO(): MovieDetailDao

    abstract fun movieDAO(): MovieDao

    abstract fun favoriteMovieDAO(): FavoriteDao

}

