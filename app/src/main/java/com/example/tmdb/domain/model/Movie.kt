package com.example.tmdb.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey val id: Int,
    val poster_path: String,
    var title: String,
    var release_date: String,
    var isFavorite: Boolean = false,
)