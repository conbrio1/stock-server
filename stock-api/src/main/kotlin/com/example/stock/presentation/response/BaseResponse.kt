package com.example.stock.presentation.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class BaseResponse<T>(
    status: HttpStatus,
    code: String,
    data: T? = null
) : ResponseEntity<BaseResponseBody<T>>(BaseResponseBody(status.value(), code, data), status) {

    companion object {
        fun <T> ok(data: T): BaseResponse<T> {
            return BaseResponse(HttpStatus.OK, "SUCCESS", data)
        }

        fun error(status: HttpStatus, code: String, message: String): BaseResponse<String> {
            return BaseResponse(status, code, message)
        }
    }
}
