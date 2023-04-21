package com.example.stock.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Document(collection = "daily_chart")
@CompoundIndex(name = "idx_symbol_timestamp", def = "{'symbol': 1, 'timestamp': -1}", unique = true)
class DailyChart private constructor(
    @Id
    val id: ObjectId,
    val symbol: String,
    var high: Double?,
    var low: Double?,
    var open: Double?,
    var close: Double?,
    var volume: Long?,
    var timestamp: Instant
) {
    constructor(
        symbol: String,
        high: Double?,
        low: Double?,
        open: Double?,
        close: Double?,
        volume: Long?,
        timestamp: Instant
    ) : this(
        id = ObjectId(),
        symbol = symbol,
        high = high,
        low = low,
        open = open,
        close = close,
        volume = volume,
        timestamp = timestamp
    )

    fun getUTCDate(): LocalDate {
        return timestamp.atZone(ZoneId.of("UTC")).toLocalDate()
    }

    fun update(newDailyChart: DailyChart) {
        high = newDailyChart.high
        low = newDailyChart.low
        open = newDailyChart.open
        close = newDailyChart.close
        volume = newDailyChart.volume
        timestamp = newDailyChart.timestamp
    }
}
