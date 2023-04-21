package com.example.stock.domain.enums

enum class InstrumentType {
    EQUITY, INDEX, CURRENCY, COMMODITY;

    companion object {
        fun from(apiParamValue: String): InstrumentType? {
            return values().find { it.name == apiParamValue }
        }
    }
}
