package com.sonasetiana.takehometest.modules.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceProvider {

    private lateinit var retrofit: Retrofit

    private fun getOkHttpClient(timeoutInSecs: Long = 60): OkHttpClient {

        // add timeout
        val builder = OkHttpClient.Builder()
            .readTimeout(timeoutInSecs, TimeUnit.SECONDS)
            .writeTimeout(timeoutInSecs, TimeUnit.SECONDS)

        // http log
        HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY }
            .let { builder.addInterceptor(it) }

        return builder.build()

    }

    fun getCustomRetrofit(retrofit: Retrofit): Retrofit {
        ServiceProvider.retrofit = retrofit
        return ServiceProvider.retrofit
    }

    fun getRetrofit(
        baseUrl: String = "",
        timeoutInSecs: Long = 60,
        okHttpClient: OkHttpClient = getOkHttpClient(timeoutInSecs),
        converterFactory: Converter.Factory = GsonConverterFactory.create(
            GsonBuilder().serializeNulls().create()
        )
    ): Retrofit {

        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .baseUrl(baseUrl)
            .build()

        return retrofit
    }

    fun getConverter(): Converter<ResponseBody, ApiError> {
        return retrofit.responseBodyConverter(ApiError::class.java, arrayOf())
    }

}