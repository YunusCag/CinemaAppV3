package com.yunuscagliyan.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yunuscagliyan.core.util.Constants.DBUtil.MOVIE_TABLE_ENTITY

@Entity(tableName = MOVIE_TABLE_ENTITY)
data class MovieEntity(
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String? = null,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "movieId")
    val movieId: Int? = null,
    @ColumnInfo(name = "original_language")
    val originalLanguage: String? = null,
    @ColumnInfo(name = "original_title")
    val originalTitle: String? = null,
    @ColumnInfo(name = "overview")
    val overview: String? = null,
    @ColumnInfo(name = "popularity")
    val popularity: Double? = null,
    @ColumnInfo(name = "poster_path")
    val posterPath: String? = null,
    @ColumnInfo(name = "release_date")
    val releaseDate: String? = null,
    @ColumnInfo(name = "title")
    val title: String? = null,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double? = null,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int? = null
)
