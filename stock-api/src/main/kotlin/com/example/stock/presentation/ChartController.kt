package com.example.stock.presentation

import com.example.stock.application.ChartSearchService
import com.example.stock.presentation.response.BaseResponse
import com.example.stock.presentation.response.ChartResponse
import org.springframework.http.HttpStatus
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
            return Mono.just(BaseResponse.error(HttpStatus.BAD_REQUEST, "days는 1 또는 5만 가능합니다."))
        }

        val symbolMetaDataMono = chartSearchService.getSymbolMetaData(symbol)

        val dailyChartFlux = chartSearchService.getDailyChart(symbol, days)
            .collectList()

        return symbolMetaDataMono.flatMap { symbolMetaData ->
            dailyChartFlux.map { dailyCharts ->
                BaseResponse.ok(ChartResponse.DailyChartInfo(symbolMetaData, dailyCharts))
            }
        }
    }
}
