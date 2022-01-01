package com.yunuscagliyan.cinemaapp.data.repository

import com.yunuscagliyan.cinemaapp.data.remote.api.TheMovieDBService
import com.yunuscagliyan.cinemaapp.data.remote.model.movie.MovieResponse
import com.yunuscagliyan.cinemaapp.domain.repository.MovieRepository

class MovieRepositoryImp constructor(
    private val api: TheMovieDBService,
) : MovieRepository {
    override suspend fun getUpComingMovies(page: Int, genreIds: List<Int>?): MovieResponse {
        return api.getUpComingMovies(page, genreIds)
    }

    override suspend fun getPopularMovies(page: Int, genreIds: List<Int>?): MovieResponse {
        return api.getPopularMovies(page, genreIds)
    }

    override suspend fun getTrendingMovies(page: Int, genreIds: List<Int>?): MovieResponse {
        return api.getTrendingMovies(page, genreIds)
    }

    override suspend fun getTopRatedMovies(page: Int, genreIds: List<Int>?): MovieResponse {
        return api.getTopRatedMovies(page, genreIds)
    }

}