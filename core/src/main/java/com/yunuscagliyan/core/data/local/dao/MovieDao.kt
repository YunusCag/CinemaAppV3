package com.yunuscagliyan.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yunuscagliyan.core.data.local.entity.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Query("SELECT * FROM movie_entity")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM movie_entity WHERE movieId = :movieId")
    suspend fun getNoteById(movieId: Int): MovieEntity?

    @Query("DELETE FROM movie_entity WHERE movieId = :movieId")
    suspend fun deleteMovie(movieId: Int)

    @Query("DELETE FROM movie_entity")
    suspend fun deleteAllMovies()
}