package com.sonasetiana.takehometest.presentation.main

import androidx.lifecycle.LiveData
import com.sonasetiana.takehometest.data.ChartModel
import com.sonasetiana.takehometest.data.ProductModel
import com.sonasetiana.takehometest.datasource.main.MainRepository
import com.sonasetiana.takehometest.modules.network.ApiError

interface MainViewModelContract {

    fun initRepository(repo: MainRepository)

    fun getChartData(params: Map<String, String>)

    fun getProductDetail(params: Map<String, String>)

    fun loadingGetChartData(): LiveData<Boolean>

    fun loadingGetProductDetail(): LiveData<Boolean>

    fun successGetChartData(): LiveData<ArrayList<ChartModel>>

    fun successGetProductDetail(): LiveData<ArrayList<ProductModel>>

    fun errorGetChartData(): LiveData<ApiError>

    fun errorGetProductDetail(): LiveData<ApiError>

}