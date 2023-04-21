package com.example.stock.domain

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ChartReader {
    /**
     * symbol에 해당하는 최근 days일간의 DailyChart를 조회한다.
     */
    fun findDailyCharts(symbol: String, days: Int): Flux<DailyChart>

    /**
     * symbol에 해당하는 SymbolMetaData를 조회한다.
     */
    fun findSymbolMetaData(symbol: String): Mono<SymbolMetaData>
}
