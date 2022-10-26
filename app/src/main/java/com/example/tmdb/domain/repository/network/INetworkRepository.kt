package com.example.tmdb.domain.repository.network

import com.example.tmdb.data.model.MovieDetailDTO
import com.example.tmdb.data.model.MovieResponseDTO
import retrofit2.Response

interface INetworkRepository {
    suspend fun getMovieDetail(moveId: Int): Response<MovieDetailDTO>

    suspend fun getMovies(page: Int): Response<MovieResponseDTO>?

    suspend fun getSearchMovieResult(query: String, page: Int): Response<MovieResponseDTO>
}