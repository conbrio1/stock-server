package com.example.stock.infra.client.dto

data class YahooChartData(
    val chart: Chart
) {
    fun getMeta() = chart.result?.get(0)?.meta

    fun getTimestamps() = chart.result?.get(0)?.timestamp

    fun getQuotes() = chart.result?.get(0)?.indicators?.quote?.get(0)

    fun isTimestampsSizeSameAsQuotesSize() =
        getTimestamps()?.size == getQuotes()?.close?.size
}

data class Chart(
    val result: List<ResultItem>? = null,
    val error: ErrorItem? = null
)

data class ErrorItem(
    val code: String,
    val description: String
)

data class ResultItem(
    val meta: Meta,
    val indicators: Indicators,
    val timestamp: List<Long>
)

data class Meta(
    val exchangeTimezoneName: String,
    val symbol: String,
    val instrumentType: String,
    val firstTradeDate: Int?,
    val timezone: String,
    val range: String?,
    val regularMarketTime: Int?,
    val dataGranularity: String?,
    val validRanges: List<String>?,
    val regularMarketPrice: Double?,
    val gmtoffset: Int?,
    val chartPreviousClose: Double?,
    val priceHint: Int?,
    val currency: String,
    val exchangeName: String,
    val currentTradingPeriod: CurrentTradingPeriod?
)

data class CurrentTradingPeriod(
    val pre: CurrentTradingPeriodItem?,
    val post: CurrentTradingPeriodItem?,
    val regular: CurrentTradingPeriodItem?
)

data class CurrentTradingPeriodItem(
    val gmtoffset: Int?,
    val timezone: String?,
    val start: Int?,
    val end: Int?
)

data class Indicators(
    val quote: List<QuoteItem>,
    val adjclose: List<AdjcloseItem>? = null
)

data class QuoteItem(
    val volume: List<Long?>,
    val high: List<Double?>,
    val low: List<Double?>,
    val close: List<Double?>,
    val open: List<Double?>
)

data class AdjcloseItem(
    val adjclose: List<Double?>
)
