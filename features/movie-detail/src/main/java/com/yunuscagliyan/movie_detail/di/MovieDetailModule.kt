package com.yunuscagliyan.movie_detail.di

import com.yunuscagliyan.movie_detail.service.MovieDetailService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.create


@Module
@InstallIn(ViewModelComponent::class)
object MovieDetailModule {
    @Provides
    @ViewModelScoped
    fun provideMovieDetailService(retrofit: Retrofit): MovieDetailService = retrofit.create()
}