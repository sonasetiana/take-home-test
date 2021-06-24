package com.sonasetiana.takehometest.datasource.main

import com.sonasetiana.takehometest.data.ChartModel
import com.sonasetiana.takehometest.data.ProductModel
import com.sonasetiana.takehometest.modules.network.ApiError

interface MainRepositoryContract {

    fun getChartData(
        params: Map<String, String>,
        successCallback: ((ArrayList<ChartModel>) -> Unit)?,
        errorCallback: ((ApiError) -> Unit)?
    )

    fun getProductDetail(
        params: Map<String, String>,
        successCallback: ((ArrayList<ProductModel>) -> Unit)?,
        errorCallback: ((ApiError) -> Unit)?
    )

}