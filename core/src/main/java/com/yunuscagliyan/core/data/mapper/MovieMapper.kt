package com.yunuscagliyan.core.data.mapper

import com.yunuscagliyan.core.data.local.entity.MovieEntity
import com.yunuscagliyan.core.data.remote.model.movie.MovieModel


fun MovieEntity.toMovieModel(): MovieModel = MovieModel(
    backdropPath = backdropPath,
    id = movieId,
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    voteCount = voteCount,
    voteAverage = voteAverage
)