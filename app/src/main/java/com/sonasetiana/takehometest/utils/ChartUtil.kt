package com.sonasetiana.takehometest.utils

import android.annotation.SuppressLint
import android.graphics.Color
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.sonasetiana.takehometest.data.ChartItem
import com.sonasetiana.takehometest.data.ChartModel
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

class AxisDateFormatter(private val data: ArrayList<String>) : ValueFormatter(){

    @SuppressLint("SimpleDateFormat")
    override fun getFormattedValue(value: Float): String {
        return if(value >= 0){
            if(data.size > value.toInt()){
                val item = data[value.toInt()]
                val formatter = SimpleDateFormat("yyyy-MM-dd").parse(item)
                SimpleDateFormat("MMM yy").format(formatter)
            }else{
                ""
            }
        }else{
            ""
        }
    }
}

class AxisRightFormatter : ValueFormatter(){

    override fun getFormattedValue(value: Float): String {
        return "${value.roundToInt()}%"
    }
}

fun ArrayList<ChartItem>.toLineDataSet(mColor: String = "#E2EBDD"): LineDataSet{
    val data = this
    val items = ArrayList<Entry>()
    var index = 0
    data.distinctBy { it.date.substring(0, 7) }.forEach { dist ->
        var total = 0f
        data.filter { it.date.equals(dist.date, true) }
                .forEach {
                    total += it.growth
                }
        items.add(Entry(index.toFloat(), total))
        index++
    }
    return LineDataSet(items, "").apply {
        mode = LineDataSet.Mode.CUBIC_BEZIER
        color = Color.parseColor(mColor)
        circleRadius = 5f
        setDrawCircleHole(false)
        setDrawCircles(false)
        valueTextColor = Color.TRANSPARENT
    }

}

fun ArrayList<ChartItem>.toCenterLineDataSet(mColor: String = "#E2EBDD"): LineDataSet{
    val data = this
    var total = 0f
    val dist = data.distinctBy { it.date.substring(0, 7) }
    data.filter { it.date.equals(dist[dist.size/2].date, true) }
        .forEach {
            total += it.growth
        }
    return LineDataSet(arrayListOf(Entry(6f, total)), "").apply {
        mode = LineDataSet.Mode.CUBIC_BEZIER
        color = Color.parseColor(mColor)
        circleRadius = 5f
        setCircleColor(Color.parseColor(mColor))
        valueTextColor = Color.TRANSPARENT
    }

}

@SuppressLint("SimpleDateFormat")
fun ArrayList<ChartModel>.toDataLegend(): Map<String, Any>{
    fun ArrayList<ChartItem>.getTotal(): Int{
        val dist = distinctBy { it.date.substring(0, 7) }
        return filter { it.date.equals(dist[dist.size/2].date, true) }
            .sumBy { it.growth.toInt() }
    }

    fun ArrayList<ChartItem>.getDate(): String{

        val dist = distinctBy { it.date.substring(0, 7) }
        val formatter = SimpleDateFormat("yyyy-MM-dd").parse(
            first { it.date.equals(dist[dist.size / 2].date, true) }.date
        )
        return SimpleDateFormat("dd MMM yyyy").format(formatter)
    }

    return mapOf(
        "first" to get(0).data.getTotal(),
        "center" to get(1).data.getTotal(),
        "last" to get(2).data.getTotal(),
        "date" to get(0).data.getDate(),
    )
}

fun ArrayList<ChartModel>.toAXisValueFormatter(): ValueFormatter{
    val items = ArrayList<String>()
    get(0).data.distinctBy { it.date.substring(0, 7) }.forEach {
        items.add(it.date)
    }
    return AxisDateFormatter(items)
}