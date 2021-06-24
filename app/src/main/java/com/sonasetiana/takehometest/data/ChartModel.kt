package com.sonasetiana.takehometest.data

import java.io.Serializable

data class ChartModel(
    var code: String,
    var data: ArrayList<ChartItem>,
    var error: String
): Serializable

data class ChartItem(
    var date: String,
    var value: Float,
    var growth: Float
): Serializable

