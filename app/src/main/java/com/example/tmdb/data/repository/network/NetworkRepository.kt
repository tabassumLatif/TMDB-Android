package com.example.tmdb.data.repository.network

import com.example.tmdb.data.model.MovieDetailDTO
import com.example.tmdb.data.model.MovieResponseDTO
import com.example.tmdb.data.remote.TmdbMoviesRequest
import com.example.tmdb.domain.repository.network.INetworkRepository
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val tmdbMoviesRequest: TmdbMoviesRequest) :
    INetworkRepository {
    override suspend fun getMovieDetail(moveId: Int): Response<MovieDetailDTO> {
        return tmdbMoviesRequest.getMovieDetail(moveId)
    }

    override suspend fun getMovies(page: Int): Response<MovieResponseDTO> {
        return tmdbMoviesRequest.getMovies(page = page)
    }

    override suspend fun getSearchMovieResult(
        query: String,
        page: Int
    ): Response<MovieResponseDTO> {
        return tmdbMoviesRequest.getSearchMovieResult(query = query, page = page)
    }
}