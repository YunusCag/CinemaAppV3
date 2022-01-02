package com.yunuscagliyan.cinemaapp.di

import com.yunuscagliyan.cinemaapp.data.remote.api.TheMovieDBService
import com.yunuscagliyan.cinemaapp.data.remote.url.API_KEY
import com.yunuscagliyan.cinemaapp.data.remote.url.BASE_URL
import com.yunuscagliyan.cinemaapp.data.repository.MovieRepositoryImp
import com.yunuscagliyan.cinemaapp.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideTheMovieDBService(): TheMovieDBService {
        val apiKeyInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()
            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
        }

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMovieDBService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: TheMovieDBService): MovieRepository {
        return MovieRepositoryImp(
            api = api
        )
    }
}