package com.example.tmdb.data.database.dao


import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tmdb.data.model.MovieDTO


@androidx.room.Dao
interface MovieDao {

    @Query("SELECT * FROM MovieDTO")
     suspend fun getMovies(): List<MovieDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: ArrayList<MovieDTO>)

    @Query("SELECT * FROM MovieDTO WHERE title like '%'||:query || '%'")
    suspend fun getMoviesByQuery(query: String):  List<MovieDTO>

}