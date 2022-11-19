package com.yunuscagliyan.core.domain

import com.yunuscagliyan.core.R
import com.yunuscagliyan.core.util.Resource
import com.yunuscagliyan.core.util.UIText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

abstract class CoreRequestUseCase<Params, Response> {

    operator fun invoke(params: Params): Flow<Resource<Response>> = flow {
        try {
            emit(Resource.Loading())
            val response = makeRequest(params)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val message = e.localizedMessage
            Timber.e(e.toString())
            emit(
                Resource.Error(
                    if (message != null)
                        UIText.DynamicString(message)
                    else UIText.StringResource(R.string.common_http_error)
                )
            )

        } catch (e: IOException) {
            Timber.e(e.toString())
            val message = e.localizedMessage
            emit(
                Resource.Error(
                    if (message != null)
                        UIText.DynamicString(message)
                    else UIText.StringResource(R.string.common_http_error)
                )
            )
        }catch (e:Exception){
            Timber.e(e.toString())
            val message = e.localizedMessage
            emit(
                Resource.Error(
                    if (message != null)
                        UIText.DynamicString(message)
                    else UIText.StringResource(R.string.common_http_error)
                )
            )
        }
    }

    abstract suspend fun makeRequest(params: Params): Response
}