package com.example.stock.presentation

import com.example.stock.application.ChartSearchService
import com.example.stock.infra.exception.StockExceptionType
import com.example.stock.infra.exception.StockRuntimeException
import com.example.stock.presentation.response.BaseResponse
import com.example.stock.presentation.response.ChartResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class ChartController(
    private val chartSearchService: ChartSearchService
) {

    @GetMapping("/daily-chart/{symbol}")
    fun getDailyChart(
        @PathVariable symbol: String,
        @RequestParam("days", defaultValue = "5") days: Int
    ): Mono<BaseResponse<ChartResponse.DailyChartInfo>> {
        if (days != 1 && days != 5) {
            return Mono.error(StockRuntimeException(StockExceptionType.INVALID_DAYS))
        }

        val symbolMetaDataMono = chartSearchService.getSymbolMetaData(symbol)

        val dailyChartListMono = chartSearchService.getDailyChart(symbol, days)
            .collectList()

        return Mono.zip(symbolMetaDataMono, dailyChartListMono)
            .flatMap {
                Mono.just(BaseResponse.ok(ChartResponse.DailyChartInfo(it.t1, it.t2)))
            }
    }
}
