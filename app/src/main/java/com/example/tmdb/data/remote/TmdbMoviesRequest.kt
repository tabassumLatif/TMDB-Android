package com.example.tmdb.data.remote

import com.example.tmdb.BuildConfig.API_KEY
import com.example.tmdb.data.model.MovieDetailDTO
import com.example.tmdb.data.model.MovieResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbMoviesRequest {
    @GET("movie/top_rated")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ): Response<MovieResponseDTO>

    @GET("search/movie")
    suspend fun getSearchMovieResult(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<MovieResponseDTO>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int, @Query("api_key") apiKey: String = API_KEY
    ): Response<MovieDetailDTO>
}