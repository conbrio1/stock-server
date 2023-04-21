package com.example.stock.presentation.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class BaseResponse<T>(
    status: HttpStatus,
    message: String,
    data: T? = null
) : ResponseEntity<BaseResponseBody<T>>(BaseResponseBody(status, message, data), status) {

    companion object {
        fun <T> ok(data: T): BaseResponse<T> {
            return BaseResponse(HttpStatus.OK, "success", data)
        }

        fun <T> error(status: HttpStatus, message: String): BaseResponse<T> {
            return BaseResponse(status, message, null)
        }
    }
}
