package com.example.tmdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tmdb.domain.model.MovieDetail

@Entity
data class MovieDetailDTO(
    @PrimaryKey val id: Int,
    val title: String,
    var overview: String,
    var revenue: String,
    var runtime: String,
    var vote_average: Double,
    val vote_count: String,
    val backdrop_path: String,
    var release_date: String,
    val imdb_id: String,
    val original_language: String,
    val popularity: Double,
    val status: String,
    val tagline: String,
    val video: String
)

fun MovieDetailDTO.toMovieDetail(): MovieDetail {
    return MovieDetail(
        this.title,
        this.overview,
        this.revenue,
        this.runtime,
        this.vote_average,
        this.vote_count,
        this.backdrop_path,
        this.release_date
    )
}