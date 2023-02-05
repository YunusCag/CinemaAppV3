package com.yunuscagliyan.core.domain.repository

import com.yunuscagliyan.core.data.local.entity.MovieEntity
import com.yunuscagliyan.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun insertMovie(movie: MovieEntity)

    fun getAllMovies(): Flow<Resource<List<MovieEntity>>>

    fun getNoteById(movieId: Int): Flow<Resource<MovieEntity?>>

    suspend fun deleteMovie(movieId: Int)

    suspend fun deleteAllMovies()
}