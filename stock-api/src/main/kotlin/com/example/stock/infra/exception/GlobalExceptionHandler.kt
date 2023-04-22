package com.example.stock.infra.exception

import com.example.stock.presentation.response.BaseResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono

@RestControllerAdvice(basePackages = ["com.example.stock"])
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(StockRuntimeException::class)
    fun handleAppException(ex: StockRuntimeException): Mono<BaseResponse<String>> {
        logger.info(ex.message)


        return Mono.just(
            BaseResponse.fail(
                status = ex.type.status,
                code = ex.type.code,
                message = ex.type.message
            )
        )
    }

    // unexpected exception
    @ExceptionHandler(Exception::class)
    fun handleUnexpectException(ex: Exception): Mono<BaseResponse<String>> {
        logger.error(ex.stackTraceToString())

        return Mono.just(
            BaseResponse.fail(
                status = HttpStatus.INTERNAL_SERVER_ERROR,
                code = HttpStatus.INTERNAL_SERVER_ERROR.name,
                message = null
            )
        )
    }
}
