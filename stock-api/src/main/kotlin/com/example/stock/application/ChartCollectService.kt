package com.example.stock.application

import com.example.stock.domain.ChartStore
import com.example.stock.domain.DailyChart
import com.example.stock.domain.SymbolMetaData
import com.example.stock.domain.enums.Currency
import com.example.stock.domain.enums.InstrumentType
import com.example.stock.infra.client.FinanceApiClient
import com.example.stock.infra.client.dto.YahooChartData
import com.example.stock.infra.client.enums.ApiValidRange
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Instant

@Service
class ChartCollectService(
    private val financeApiClient: FinanceApiClient,
    private val chartStore: ChartStore
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun collectDailyChart(symbol: String, range: ApiValidRange) {
        // Get chart data from API
        val chartData = financeApiClient.getDailyChartDataBySymbol(symbol, range)
            .doOnNext {
                if (it == null) {
                    logger.warn("No chart data received from API call for symbol: $symbol")
                }
            }
            .share()

        // Save symbol meta data
        chartData.flatMap {
            dtoToSymbolMetaData(it, symbol)
        }.flatMap {
            chartStore.saveSymbolMetaData(it)
        }.doOnNext {
            logger.debug("Saved symbol meta data for symbol: $symbol")
        }.subscribe()

        // Save daily chart
        chartData.flatMapMany {
            dtoToDailyChart(it, symbol)
        }.flatMap {
            chartStore.upsertDailyChart(it)
        }.doOnNext {
            logger.debug("Update daily chart for symbol: {}, timestamp: {}", symbol, it.timestamp)
        }.subscribe()
    }

    private fun dtoToSymbolMetaData(
        chartData: YahooChartData,
        symbol: String
    ): Mono<SymbolMetaData> {
        val meta = chartData.getMeta()
            ?: return Mono.error(IllegalArgumentException("No chart data found for symbol: $symbol"))

        return Mono.just(
            SymbolMetaData(
                symbol = symbol,
                currency = Currency.from(meta.currency)
                    ?: return Mono.error(IllegalArgumentException("No currency found for symbol: $symbol")),
                instrumentType = InstrumentType.from(meta.instrumentType)
                    ?: return Mono.error(IllegalArgumentException("No instrument type found for symbol: $symbol")),
                exchangeName = meta.exchangeName,
                exchangeTimezoneName = meta.exchangeTimezoneName
            )
        )
    }

    private fun dtoToDailyChart(
        chartData: YahooChartData,
        symbol: String
    ): Flux<DailyChart> {
        val timestamps = chartData.getTimestamps()
            ?: return Flux.error(IllegalArgumentException("No timestamp data found for symbol: $symbol"))
        val quote = chartData.getQuotes()
            ?: return Flux.error(IllegalArgumentException("No quote data found for symbol: $symbol"))

        if (!chartData.isTimestampsSizeSameAsQuotesSize()) {
            return Flux.error(IllegalArgumentException("Timestamp and quote data size mismatch for symbol: $symbol"))
        }

        return Flux.fromIterable(timestamps)
            .index()
            .flatMap {
                val index = it.t1.toInt()
                val timestamp = it.t2

                Mono.just(
                    DailyChart(
                        symbol = symbol,
                        high = quote.high[index],
                        low = quote.low[index],
                        open = quote.open[index],
                        close = quote.close[index],
                        volume = quote.volume[index],
                        timestamp = Instant.ofEpochSecond(timestamp)
                    )
                )
            }
    }
}
