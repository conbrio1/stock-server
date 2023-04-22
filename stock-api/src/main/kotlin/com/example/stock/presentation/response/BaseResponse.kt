package com.example.stock.presentation.response

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

class BaseResponse<T>(
    val status: HttpStatus,
    val body: BaseResponseBody<T>?,
    val headers: MultiValueMap<String, String>? = null
) : ResponseEntity<BaseResponseBody<T>>(body, headers, status) {

    companion object {
        private val defaultHeader = LinkedMultiValueMap<String, String>().apply {
            add("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        }

        fun <T> ok(data: T): BaseResponse<T> {
            return BaseResponse(
                status = HttpStatus.OK,
                body = BaseResponseBody(HttpStatus.OK.value(), HttpStatus.OK.name, data),
                headers = defaultHeader
            )
        }

        fun fail(status: HttpStatus, code: String, message: String?): BaseResponse<String> {
            return BaseResponse(
                status = status,
                body = BaseResponseBody(status.value(), code, message),
                headers = defaultHeader
            )
        }
    }
}
