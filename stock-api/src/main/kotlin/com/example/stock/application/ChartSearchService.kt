package com.example.stock.application

import com.example.stock.domain.ChartReader
import com.example.stock.domain.DailyChart
import com.example.stock.domain.SymbolMetaData
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ChartSearchService(
    private val chartReader: ChartReader
) {

    fun getSymbolMetaData(symbol: String): Mono<SymbolMetaData> {
        return chartReader.findSymbolMetaData(symbol)
//            .switchIfEmpty { Mono.error(SymbolNotFoundException(symbol)) }
    }

    fun getDailyChart(symbol: String, days: Int): Flux<DailyChart> {
        return chartReader.findDailyCharts(symbol, days)
//            .switchIfEmpty { Mono.error(DailyChartNotFoundException(symbol)) }
    }
}
