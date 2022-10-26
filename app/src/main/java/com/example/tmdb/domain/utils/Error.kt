package com.example.tmdb.domain.utils

enum class Error(val message: String) {
    NETWORK_ERROR("Internet seems to be offline"),
    NO_RESULT_FOUND("No result Found"),
    FAVORITE_NOT_FOUND("No movie is added in favorite")
}