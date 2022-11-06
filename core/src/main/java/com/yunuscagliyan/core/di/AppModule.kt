package com.yunuscagliyan.core.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.yunuscagliyan.core.data.local.preference.AppPreference
import com.yunuscagliyan.core.data.local.preference.Preferences
import com.yunuscagliyan.core.util.Constants
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
}