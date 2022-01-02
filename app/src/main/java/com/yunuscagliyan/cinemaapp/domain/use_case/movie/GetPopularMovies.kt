package com.yunuscagliyan.cinemaapp.domain.use_case.movie

import com.yunuscagliyan.cinemaapp.core.util.Resource
import com.yunuscagliyan.cinemaapp.data.remote.model.movie.MovieResponse
import com.yunuscagliyan.cinemaapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetPopularMovies @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(
        page:Int=1,
        genreIds: List<Int>?,
    ): Flow<Resource<MovieResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response=repository.getPopularMovies(page, genreIds)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error(e.message))

        }
    }
}