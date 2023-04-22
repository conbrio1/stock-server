package com.example.stock.infra.exception

import org.springframework.http.HttpStatus

enum class StockExceptionType(
    val status: HttpStatus,
    val code: String,
    val message: String
) {
    SYMBOL_NOT_FOUND(
        status = HttpStatus.NOT_FOUND,
        code = "SYMBOL_NOT_FOUND",
        message = "요청받은 종목 코드가 존재하지 않습니다."
    ),
    INVALID_DAYS(
        status = HttpStatus.BAD_REQUEST,
        code = "INVALID_DAYS",
        message = "유효하지 않은 days 파라미터에 대한 요청입니다."
    ),
    DAILY_CHART_NOT_FOUND(
        status = HttpStatus.NOT_FOUND,
        code = "DAILY_CHART_NOT_FOUND",
        message = "요청받은 종목 코드에 대한 일별 차트 정보가 존재하지 않습니다."
    );
}
