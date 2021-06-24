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

fun ArrayList<ChartItem>.toLineDataSet(label: String = "", mColor: String = "#E2EBDD"): LineDataSet{
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
    return LineDataSet(items, label).apply {
        mode = LineDataSet.Mode.CUBIC_BEZIER
        color = Color.parseColor(mColor)
        circleRadius = 5f
        setCircleColor(Color.parseColor(mColor))
    }

}

fun ArrayList<ChartModel>.toAXisValueFormatter(): ValueFormatter{
    val items = ArrayList<String>()
    get(0).data.distinctBy { it.date.substring(0, 7) }.forEach {
        items.add(it.date)
    }
    return AxisDateFormatter(items)
}