package com.sonasetiana.takehometest.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sonasetiana.takehometest.data.ChartModel
import com.sonasetiana.takehometest.data.ProductModel
import com.sonasetiana.takehometest.datasource.main.MainRepository
import com.sonasetiana.takehometest.modules.network.ApiError

class MainViewModel: ViewModel(), MainViewModelContract {

    private var repo : MainRepository? = null

    private val loadingChart = MutableLiveData<Boolean>()

    private val loadingProductDetail = MutableLiveData<Boolean>()

    private val successChart = MutableLiveData<ArrayList<ChartModel>>()

    private val successProductDetail = MutableLiveData<ArrayList<ProductModel>>()

    private val errorChart = MutableLiveData<ApiError>()

    private val errorProductDetail = MutableLiveData<ApiError>()

    override fun initRepository(repo: MainRepository) {
        this.repo = repo
    }

    override fun getChartData(params: Map<String, String>) {
        loadingChart.value = true
        repo?.getChartData(
                params = params,
                successCallback = {
                    loadingChart.value = false
                    successChart.value = it
                    
                },
                errorCallback = {
                    loadingChart.value = false
                    errorChart.value = it
                }
        )
    }

    override fun getProductDetail(params: Map<String, String>) {
        loadingProductDetail.value = true
        repo?.getProductDetail(
                params = params,
                successCallback = {
                    loadingProductDetail.value = false
                    successProductDetail.value = it
                },
                errorCallback = {
                    loadingProductDetail.value = false
                    errorProductDetail.value = it
                }
        )
    }

    override fun loadingGetChartData(): LiveData<Boolean> = loadingChart

    override fun loadingGetProductDetail(): LiveData<Boolean> = loadingProductDetail

    override fun successGetChartData(): LiveData<ArrayList<ChartModel>> = successChart

    override fun successGetProductDetail(): LiveData<ArrayList<ProductModel>> = successProductDetail

    override fun errorGetChartData(): LiveData<ApiError> = errorChart

    override fun errorGetProductDetail(): LiveData<ApiError> = errorProductDetail


}