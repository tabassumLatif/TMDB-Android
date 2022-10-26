package com.example.tmdb.data.model

import com.example.tmdb.domain.model.Movie
import com.example.tmdb.domain.model.MovieResponse


data class MovieResponseDTO(
    val page: Int,
    val results: ArrayList<MovieDTO>,
    val total_results: Int,
    val total_pages: Int
)

fun MovieResponseDTO.toMovieResponse(): MovieResponse {
    return MovieResponse(this.results.map {
        Movie(
            it.id,
            it.poster_path ?: "",
            it.title ?: "",
            it.release_date ?: ""
        )
    } as ArrayList<Movie>)
}