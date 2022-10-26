package com.example.tmdb.domain.utils


sealed class Resource(val data: Any?, val error: String?) {
    class Success(data: Any?) : Resource(data, null)
    class Error(error: String?) : Resource(null, error)
}
