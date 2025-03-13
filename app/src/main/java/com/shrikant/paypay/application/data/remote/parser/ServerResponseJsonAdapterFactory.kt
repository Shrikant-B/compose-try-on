package com.shrikant.paypay.application.data.remote.parser

import com.shrikant.paypay.application.data.model.base.ServerResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

class ServerResponseJsonAdapterFactory : JsonAdapter.Factory {

    override fun create(
        type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi
    ): JsonAdapter<*>? {
        // Log the type to understand what is being passed
        if (Types.getRawType(type) == ServerResponse::class.java) {
            return ServerResponseMoshiJsonAdapter(moshi)
        }
        return null
    }
}