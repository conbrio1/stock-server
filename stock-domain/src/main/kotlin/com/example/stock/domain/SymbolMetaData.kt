package com.example.stock.domain

import com.example.stock.domain.enums.Currency
import com.example.stock.domain.enums.InstrumentType
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

@Document(collection = "symbol_metadata")
class SymbolMetaData(
    @MongoId
    val symbol: String,
    val currency: Currency,
    val instrumentType: InstrumentType,
    val exchangeName: String,
    val exchangeTimezoneName: String
)
