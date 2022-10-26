package com.example.tmdb.data.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tmdb.data.model.MovieDetailDTO

@androidx.room.Dao
interface MovieDetailDao {

    @Query("SELECT * FROM MovieDetailDTO WHERE id==:id")
    suspend fun getDetail(id: Int): MovieDetailDTO

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movieDetail: MovieDetailDTO)

}