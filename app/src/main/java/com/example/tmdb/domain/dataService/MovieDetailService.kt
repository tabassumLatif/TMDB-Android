package com.example.tmdb.domain.dataService

import com.example.tmdb.data.model.MovieDetailDTO
import com.example.tmdb.data.model.toMovieDetail
import com.example.tmdb.domain.utils.Resource
import com.example.tmdb.domain.repository.db.IMovieDetailRepository
import com.example.tmdb.domain.repository.network.INetworkRepository
import com.example.tmdb.domain.utils.Error
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject

class MovieDetailService @Inject constructor(
    private val networkRepository: INetworkRepository,
    private val dbMovieDetailRepository: IMovieDetailRepository,
) {
    suspend fun getMovieDetail(moveId: Int): Resource {
        return try {
            val response = networkRepository.getMovieDetail(moveId)

            if (response.isSuccessful) {
                response.body()?.let { dbMovieDetailRepository.insertMovieDetail(it) }
                Resource.Success(response.body()?.toMovieDetail())
            } else {
                getMovieDetailFromDb(movieId = moveId, response = response)
            }
        } catch (unKnownHost: UnknownHostException) {
            getMovieDetailFromDb(movieId = moveId, error = Error.NETWORK_ERROR.message)
        } catch (exception: Exception) {
            getMovieDetailFromDb(movieId = moveId, error = Error.NO_RESULT_FOUND.message)
        }
    }

    private suspend fun getMovieDetailFromDb(
        movieId: Int,
        response: Response<MovieDetailDTO>? = null,
        error: String? = null
    ): Resource {
        val result = dbMovieDetailRepository.getMovieDetailFromDB(movieId)
        return if (result != null) {
            Resource.Success(
                result.toMovieDetail()
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
}