package com.example.tmdb.domain.model

data class MovieDetail(
    val title: String,
    var overview: String,
    var revenue: String,
    var runtime: String,
    var vote_average: Double,
    val vote_count: String,
    val backdrop_path: String,
    var release_date: String
)