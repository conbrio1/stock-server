package com.example.stock.infra.client

import com.example.stock.infra.client.dto.YahooChartData
import com.example.stock.infra.client.enums.ApiValidRange
import reactor.core.publisher.Mono

interface FinanceApiClient {
    fun getDailyChartDataBySymbol(symbol: String, range: ApiValidRange): Mono<YahooChartData>
}
