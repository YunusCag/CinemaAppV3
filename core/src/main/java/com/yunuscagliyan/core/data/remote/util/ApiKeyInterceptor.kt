package com.yunuscagliyan.core.data.remote.util

import com.yunuscagliyan.core.BuildConfig
import com.yunuscagliyan.core.util.Constants.NetworkQueryParamKey.apiKey
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter(apiKey, BuildConfig.API_KEY)
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        val response: Response = chain.proceed(request)
        val cacheControl = CacheControl.Builder()
            .maxAge(10, TimeUnit.DAYS)
            .build()
        return response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()
    }
}