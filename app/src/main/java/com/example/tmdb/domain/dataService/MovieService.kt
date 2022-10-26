package com.example.tmdb.domain.dataService

import com.example.tmdb.data.model.MovieResponseDTO
import com.example.tmdb.data.model.toMovieResponse
import com.example.tmdb.domain.utils.Resource
import com.example.tmdb.domain.repository.db.IFavoriteMovieRepository
import com.example.tmdb.domain.repository.db.IMoviesRepository
import com.example.tmdb.domain.repository.network.INetworkRepository
import com.example.tmdb.domain.utils.Error
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject


class MovieService @Inject constructor(
    private val networkRepository: INetworkRepository,
    private val dbMovieRepository: IMoviesRepository,
    private val dbFavoriteMovieRepository: IFavoriteMovieRepository,
) {

    suspend fun getMovies(page: Int): Resource {
        return try {
            val response = networkRepository.getMovies(page)
            if (response != null && response.isSuccessful) {
                response.body()?.results?.let { dbMovieRepository.insertMovies(it) }
                val movieResponse = response.body()?.toMovieResponse()
                movieResponse?.results?.forEach {
                    it.isFavorite = dbFavoriteMovieRepository.isFavoriteExist(it.id)
                }
                Resource.Success(movieResponse)
            } else {
                getMoviesFromDb(response = response)
            }
        } catch (unKnownHost: UnknownHostException) {
            getMoviesFromDb(error = Error.NETWORK_ERROR.message)
        } catch (exception: Exception) {
            getMoviesFromDb(error = Error.NO_RESULT_FOUND.message)
        }
    }

    private suspend fun getMoviesFromDb(
        response: Response<MovieResponseDTO>? = null,
        error: String? = null,
    ): Resource {
        val result = dbMovieRepository.getMoviesFromDB()
        return if (result.isEmpty().not()) {
            val movieResponse = MovieResponseDTO(
                1,
                result as ArrayList,
                result.size,
                1
            ).toMovieResponse()
            movieResponse.results.forEach {
                it.isFavorite = dbFavoriteMovieRepository.isFavoriteExist(it.id)
            }
            Resource.Success(
                movieResponse
            )
        } else {
            Resource.Error(
                if (response == null) {
                    error
                } else {
                    response.message()
                }
            )
        }
    }

    suspend fun getSearchMovieResult(query: String, page: Int): Resource {
        return try {
            val response = networkRepository.getSearchMovieResult(query, page)
            if (response.isSuccessful) {
                Resource.Success(response.body()?.toMovieResponse())
            } else {
                Resource.Error(
                    Error.NO_RESULT_FOUND.message
                )
            }
        } catch (unKnownHost: UnknownHostException) {
            Resource.Error(Error.NETWORK_ERROR.message)
        } catch (exception: Exception) {
            Resource.Error(Error.NO_RESULT_FOUND.message)
        }
    }

}