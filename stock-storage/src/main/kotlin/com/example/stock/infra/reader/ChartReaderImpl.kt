package com.example.stock.infra.reader

import com.example.stock.domain.ChartReader
import com.example.stock.domain.DailyChart
import com.example.stock.domain.SymbolMetaData
import com.example.stock.infra.repository.DailyChartRepository
import com.example.stock.infra.repository.SymbolMetaDataRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ChartReaderImpl(
    private val dailyChartRepository: DailyChartRepository,
    private val symbolMetaDataRepository: SymbolMetaDataRepository
) : ChartReader {
    override fun findDailyCharts(symbol: String, days: Int): Flux<DailyChart> {
        return dailyChartRepository.findBySymbol(
            symbol,
            PageRequest.of(0, days, Sort.by(Sort.Direction.DESC, "timestamp"))
        )
    }

    override fun findSymbolMetaData(symbol: String): Mono<SymbolMetaData> {
        return symbolMetaDataRepository.findBySymbol(symbol)
    }
}
