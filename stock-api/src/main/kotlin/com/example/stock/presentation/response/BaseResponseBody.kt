package com.example.stock.presentation.response

import org.springframework.http.HttpStatus

data class BaseResponseBody<T>(
    val status: HttpStatus,
    val message: String,
    val data: T? = null
)
