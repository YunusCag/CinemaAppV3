package com.yunuscagliyan.core.data.local.repository

import com.yunuscagliyan.core.R
import com.yunuscagliyan.core.data.local.dao.MovieDao
import com.yunuscagliyan.core.data.local.entity.MovieEntity
import com.yunuscagliyan.core.domain.repository.MovieRepository
import com.yunuscagliyan.core.util.Resource
import com.yunuscagliyan.core.util.UIText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class MovieRepositoryImp constructor(
    private val dao: MovieDao
) : MovieRepository {
    override suspend fun insertMovie(movie: MovieEntity) {
        dao.insertMovie(movie = movie)
    }

    override fun getAllMovies(): Flow<Resource<List<MovieEntity>>> = flow {
        try {
            emit(Resource.Loading())
            val movies = dao.getAllMovies()
            emit(Resource.Success(movies))
        } catch (e: Exception) {
            Timber.e(e.toString())
            val message = e.localizedMessage
            emit(
                Resource.Error(
                    if (message != null)
                        UIText.DynamicString(message)
                    else UIText.StringResource(R.string.common_http_error)
                )
            )
        }
    }

    override fun getNoteById(movieId: Int): Flow<Resource<MovieEntity?>> = flow {
        try {
            emit(Resource.Loading())
            val movie = dao.getNoteById(movieId)
            emit(Resource.Success(movie))
        } catch (e: Exception) {
            Timber.e(e.toString())
            val message = e.localizedMessage
            emit(
                Resource.Error(
                    if (message != null)
                        UIText.DynamicString(message)
                    else UIText.StringResource(R.string.common_http_error)
                )
            )
        }
    }

    override suspend fun deleteMovie(movieId: Int) {
        try {
            dao.deleteMovie(movieId)
        } catch (e: Exception) {
            Timber.e(e.toString())
        }
    }

    override suspend fun deleteAllMovies() {
        try {
            dao.deleteAllMovies()
        } catch (e: Exception) {
            Timber.e(e.toString())
        }
    }

}