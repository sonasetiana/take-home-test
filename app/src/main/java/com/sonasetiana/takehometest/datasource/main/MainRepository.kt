package com.sonasetiana.takehometest.datasource.main

import com.google.gson.Gson
import com.sonasetiana.takehometest.data.ChartModel
import com.sonasetiana.takehometest.data.ProductDetailResponse
import com.sonasetiana.takehometest.data.ProductModel
import com.sonasetiana.takehometest.modules.network.ApiCallback
import com.sonasetiana.takehometest.modules.network.ApiError
import okhttp3.ResponseBody
import org.json.JSONObject

class MainRepository(
    private val dataSource: MainDataSource
): MainRepositoryContract {
    
    override fun getChartData(
        params: Map<String, String>,
        successCallback: ((ArrayList<ChartModel>) -> Unit)?,
        errorCallback: ((ApiError) -> Unit)?
    ) {
        dataSource.getChartData(params)
            .enqueue(object : ApiCallback<ResponseBody>(){
                override fun onSuccess(response: ResponseBody) {
                   try{
                       val items = ArrayList<ChartModel>()
                       val obj = JSONObject(response.string()).getJSONObject("data")
                       obj.keys().forEach { key ->
                           val item = Gson().fromJson(obj.getJSONObject(key).toString(), ChartModel::class.java).apply { code = key }
                           items.add(item)
                       }
                       successCallback?.invoke(items)
                   }catch (e: Exception){
                       errorCallback?.invoke(ApiError(code = 0, error_message = "Json parse error"))
                   }
                }

                override fun onError(error: ApiError) {
                    errorCallback?.invoke(error)
                }

            })
    }

    override fun getProductDetail(
        params: Map<String, String>,
        successCallback: ((ArrayList<ProductModel>) -> Unit)?,
        errorCallback: ((ApiError) -> Unit)?
    ) {
        dataSource.getProductDetail(params)
            .enqueue(object : ApiCallback<ProductDetailResponse>(){
                override fun onSuccess(response: ProductDetailResponse) {
                    successCallback?.invoke(response.data)
                }

                override fun onError(error: ApiError) {
                    errorCallback?.invoke(error)
                }

            })
        
    }


}