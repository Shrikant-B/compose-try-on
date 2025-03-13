package com.shrikant.paypay.application.data.model.base.result

sealed interface Result<out T> {
    class Success<T>(val data: T) : Result<T>
    class Error(val exception: Throwable) : Result<Nothing>
}