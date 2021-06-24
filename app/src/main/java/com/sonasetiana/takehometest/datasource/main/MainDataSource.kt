package com.sonasetiana.takehometest.datasource.main

import com.sonasetiana.takehometest.BuildConfig
import com.sonasetiana.takehometest.data.ProductDetailResponse
import com.sonasetiana.takehometest.modules.network.ServiceProvider
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MainDataSource {

    @GET("chart")
    fun getChartData(@QueryMap params: Map<String, String>) : Call<ResponseBody>

    @GET("detail")
    fun getProductDetail(@QueryMap params: Map<String, String>) : Call<ProductDetailResponse>

    companion object Factory {

        private const val MODULE = "takehometest/apps/compare/"

        fun create() : MainDataSource {
            return ServiceProvider.getRetrofit(BuildConfig.BASE_URL + MODULE)
                .create(MainDataSource::class.java)
        }
    }
}