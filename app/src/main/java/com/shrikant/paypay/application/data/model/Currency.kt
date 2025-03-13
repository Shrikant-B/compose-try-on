package com.shrikant.paypay.application.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Currency(
    val code: String,
    val name: String
)