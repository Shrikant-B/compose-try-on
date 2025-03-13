package com.shrikant.paypay.application.data.model.base.error

import com.shrikant.paypay.application.data.model.base.ServerResponse
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServerError(
    val error: Boolean,
    val status: Long,
    val message: String,
    val description: String
) : ServerResponse