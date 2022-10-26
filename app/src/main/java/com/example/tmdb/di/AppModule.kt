package com.example.tmdb.di

import android.content.Context
import androidx.room.Room
import com.example.tmdb.BuildConfig.BASE_URL
import com.example.tmdb.data.database.Database
import com.example.tmdb.data.remote.TmdbMoviesRequest
import com.example.tmdb.data.repository.*
import com.example.tmdb.data.repository.db.FavoriteMovieRepository
import com.example.tmdb.data.repository.db.MovieDetailRepository
import com.example.tmdb.data.repository.db.MoviesRepository
import com.example.tmdb.data.repository.network.NetworkRepository
import com.example.tmdb.domain.repository.*
import com.example.tmdb.domain.repository.db.IFavoriteMovieRepository
import com.example.tmdb.domain.repository.db.IMovieDetailRepository
import com.example.tmdb.domain.repository.db.IMoviesRepository
import com.example.tmdb.domain.repository.network.INetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getNetworkRepo(tmdbMoviesRequest: TmdbMoviesRequest): INetworkRepository {
        return NetworkRepository(tmdbMoviesRequest)
    }

    @Singleton
    @Provides
    fun getMoviesRepository(database: Database): IMoviesRepository {
        return MoviesRepository(database)
    }
    @Singleton
    @Provides
    fun getMovieDetailRepository(database: Database): IMovieDetailRepository {
        return MovieDetailRepository(database)
    }
    @Singleton
    @Provides
    fun getFavoriteMovieRepository(database: Database): IFavoriteMovieRepository {
        return FavoriteMovieRepository(database)
    }

    @Singleton
    @Provides
    fun getApiInstance(): TmdbMoviesRequest {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbMoviesRequest::class.java)
    }

    @Singleton
    @Provides
    fun getDatabaseInstance(@ApplicationContext context: Context): Database {
        val databaseName = "movie_db"
        return Room.databaseBuilder(
            context,
            Database::class.java, databaseName
        ).fallbackToDestructiveMigration()
            .enableMultiInstanceInvalidation()
            .build()
    }
}