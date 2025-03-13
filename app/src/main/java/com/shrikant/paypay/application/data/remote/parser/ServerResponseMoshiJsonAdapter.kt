package com.shrikant.paypay.application.data.remote.parser

import com.shrikant.paypay.application.data.model.ExchangeRate
import com.shrikant.paypay.application.data.model.base.ServerResponse
import com.shrikant.paypay.application.data.model.base.error.ServerError
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi

class ServerResponseMoshiJsonAdapter(
    moshi: Moshi
) : JsonAdapter<ServerResponse>() {
    private val exchangeRateAdapter = moshi.adapter(ExchangeRate::class.java)
    private val serverErrorAdapter = moshi.adapter(ServerError::class.java)

    override fun fromJson(reader: JsonReader): ServerResponse? {
        val jsonValue = reader.readJsonValue()
        return when {
            (jsonValue as? Map<*, *>)?.containsKey("error") == true ->
                serverErrorAdapter.fromJsonValue(jsonValue)

            else -> exchangeRateAdapter.fromJsonValue(jsonValue)
        }
    }

    override fun toJson(writer: JsonWriter, value: ServerResponse?) {
        if (value == null) {
            writer.endObject()
            return
        }

        writer.beginObject()
        when (value) {
            is ExchangeRate -> exchangeRateAdapter.toJson(writer, value)
            is ServerError -> serverErrorAdapter.toJson(writer, value)
        }
        writer.endObject()
    }
}