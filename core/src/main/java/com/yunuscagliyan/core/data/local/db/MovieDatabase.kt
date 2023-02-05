package com.yunuscagliyan.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yunuscagliyan.core.data.local.dao.MovieDao
import com.yunuscagliyan.core.data.local.entity.MovieEntity
import com.yunuscagliyan.core.util.Constants.DBUtil.MOVIE_DATABASE_VERSION

@Database(
    entities = [MovieEntity::class],
    exportSchema = false,
    version = MOVIE_DATABASE_VERSION
)
abstract class MovieDatabase:RoomDatabase() {
    abstract val dao:MovieDao
}