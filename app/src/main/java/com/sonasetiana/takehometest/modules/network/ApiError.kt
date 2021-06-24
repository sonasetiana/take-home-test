package com.sonasetiana.takehometest.modules.network

import java.io.Serializable

data class ApiError(
    var code: Int,
    var error_message: String,
    var status: String? = null
) : Serializable
