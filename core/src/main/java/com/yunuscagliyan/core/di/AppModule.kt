package com.yunuscagliyan.core.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.yunuscagliyan.core.data.local.db.MovieDatabase
import com.yunuscagliyan.core.data.local.preference.AppPreference
import com.yunuscagliyan.core.data.local.preference.Preferences
import com.yunuscagliyan.core.data.local.repository.MovieRepositoryImp
import com.yunuscagliyan.core.domain.repository.MovieRepository
import com.yunuscagliyan.core.util.Constants
import com.yunuscagliyan.core.util.Constants.DBUtil.MOVIE_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(
        app: Application
    ): SharedPreferences {
        return app.getSharedPreferences(Constants.SharedPreferenceUtil.sharedPrefName, MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): Preferences {
        return AppPreference(sharedPref = sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideMovieDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            MOVIE_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(movieDatabase: MovieDatabase): MovieRepository {
        return MovieRepositoryImp(
            dao = movieDatabase.dao
        )
    }
}