package com.sonasetiana.takehometest.data

import android.annotation.SuppressLint
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

data class ProductModel(
    var code: String,
    var name: String,
    var details: ProductDetails
): Serializable

data class ProductDetails(
    var category : String,
    var category_id : Int,
    var currency : String,
    var custody : String,
    var inception_date : String,
    var im_avatar : String,
    var im_name : String,
    var min_balance : Long,
    var min_redemption : Int,
    var min_subscription : Long,
    var nav : Float,
    var return_cur_year : Float,
    var return_five_year : Float,
    var return_four_year : Float,
    var return_inception_growth : Float,
    var return_one_day : Float,
    var return_one_month : Float,
    var return_one_week : Float,
    var return_one_year : Float,
    var return_six_month : Float,
    var return_three_month : Float,
    var return_three_year : Float,
    var return_two_year : Float,
    var total_unit : Long,
    var type : String,
    var type_id : Int
): Serializable{
    
    private fun getYieldReward(period: String) = when(period){
        "1D" -> "$return_one_day% / 1 hari"
        "1W" -> "$return_one_week% / 1 min"
        "1M" -> "$return_one_month% / 1 bln"
        "3M" -> "$return_three_month% / 3 bln"
        "6M" -> "$return_six_month% / 6 bln"
        "1Y" -> "$return_one_year% / 1 thn"
        "2Y" -> "$return_two_year% / 2 thn"
        "3Y" -> "$return_three_year% / 3 thn"
        "4Y" -> "$return_four_year% / 4 thn"
        "5Y" -> "$return_five_year% / 5 thn"
        else -> "$return_cur_year% / 1 thn"
    }

    private fun getTimePeriod(period: String) = when(period){
        "1D" -> "1 Hari"
        "1W" -> "1 Minggu"
        "1M" -> "1 Bulan"
        "3M" -> "3 Bulan"
        "6M" -> "6 Bulan"
        "1Y" -> "1 Tahun"
        "2Y" -> "2 Tahun"
        "3Y" -> "3 Tahun"
        "4Y" -> "4 Tahun"
        "5Y" -> "5 Tahun"
        else -> "1 Tahun"
    }

    private fun getLevelOfRisk(period: String) : String {
        val number = when(period){
            "1D" -> return_one_day
            "1W" -> return_one_week
            "1M" -> return_one_month
            "3M" -> return_three_month
            "6M" -> return_six_month
            "1Y" -> return_one_year
            "2Y" -> return_two_year
            "3Y" -> return_three_year
            "4Y" -> return_four_year
            "5Y" -> return_five_year
            else -> return_cur_year
        }

        return if(number > 1.0) "Tinggi" else "Rendah"
    }

    private fun Long.getPriceFormat() = when{
        this >= 1_000_000_000_000 -> "${this / 1_000_000_000_000} Triliun"
        this >= 1_000_000_000 -> "${this / 1_000_000_000} Miliar"
        this >= 1_000_000-> "${this / 1_000_000} Juta"
        else -> "${this / 1_000} Ribu"
    }

    @SuppressLint("SimpleDateFormat")
    private fun parseDate(): String{
        val formatter = SimpleDateFormat("yyyy-MM-dd").parse(inception_date)
        return SimpleDateFormat("dd MMM yyyy").format(formatter)
    }

    fun toList(period: String, isShowTitle: Boolean = false, bgColor: Int) = arrayListOf(
        ProductChildAdapterData("Jenis reksa dana", type, isShowTitle, bgColor),
        ProductChildAdapterData("Imbal hasil", getYieldReward(period), isShowTitle, bgColor),
        ProductChildAdapterData("Dana kelolaan", total_unit.getPriceFormat(), isShowTitle, bgColor),
        ProductChildAdapterData("Min. pembelian", min_subscription.getPriceFormat(), isShowTitle, bgColor),
        ProductChildAdapterData("Jangka waktu", getTimePeriod(period), isShowTitle, bgColor),
        ProductChildAdapterData("Tingkat risiko", getLevelOfRisk(period), isShowTitle, bgColor),
        ProductChildAdapterData("Peluncuran", parseDate(), isShowTitle, bgColor),
    )
}

data class ProductDetailResponse(var data: ArrayList<ProductModel>): Serializable

data class ProductChildAdapterData(
    var title: String,
    var data: String,
    var showTitle: Boolean,
    var bgColor: Int
): Serializable