package com.sonasetiana.takehometest.modules.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException

abstract class ApiCallback<T>: Callback<T> {

    companion object {
        const val MSG_RTO = "Connection timeout. Make sure you have an internet connection."
        const val MSG_UNKNOWN = "Oops, something wrong. Please try again later."
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        val body = response.body()
        if (body == null) onError(parseError(response))
        else onSuccess(body)
    }

    override fun onFailure(call: Call<T>, e: Throwable) {
        when (e) {
            is HttpException -> {
                val response = e.response()

                val error =
                    if (response == null) ApiError(
                        0,
                        MSG_RTO
                    )
                    else parseError(response)

                error.code = response?.code() ?: 0
                onError(error)
            }
            is SocketTimeoutException -> onError(
                ApiError(
                    0,
                    MSG_RTO
                )
            )
            else -> onError(
                ApiError(
                    99,
                    MSG_UNKNOWN
                )
            )
        }
    }

    abstract fun onSuccess(response: T)

    abstract fun onError(error: ApiError)

    private fun parseError(response: Response<*>): ApiError {
        val converter = ServiceProvider.getConverter()
        return try {
            val body = response.errorBody()
            val error =
                if (body == null) ApiError(
                    response.code(),
                    MSG_RTO
                )
                else converter.convert(body)

            error?.code = response.code()
            error ?: ApiError(
                response.code(),
                MSG_RTO
            )
        } catch (e: Exception) {
            ApiError(
                response.code(),
                MSG_UNKNOWN
            )
        }
    }

}