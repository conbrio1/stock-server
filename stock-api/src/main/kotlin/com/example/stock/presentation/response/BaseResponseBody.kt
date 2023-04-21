package com.example.stock.presentation.response

data class BaseResponseBody<T>(
    val status: Int,
    val code: String,
    val data: T? = null
)
