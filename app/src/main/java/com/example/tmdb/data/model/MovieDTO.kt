package com.example.tmdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieDTO(
    @PrimaryKey val id: Int,
    val poster_path: String?,
    val title: String?,
    val release_date: String?,
    val adult: Boolean?,
    val original_language: String?,
    val popularity: Double?,
    val video: Boolean?
)
