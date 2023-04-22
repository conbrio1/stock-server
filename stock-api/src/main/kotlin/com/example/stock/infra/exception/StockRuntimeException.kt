package com.example.stock.infra.exception

class StockRuntimeException(
    val type: StockExceptionType
) : RuntimeException(type.message)
