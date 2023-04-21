package com.example.stock.domain.enums

enum class Currency {
    KRW, USD;

    companion object {
        fun from(apiParamValue: String): Currency? {
            return values().find { it.name == apiParamValue }
        }
    }
}
