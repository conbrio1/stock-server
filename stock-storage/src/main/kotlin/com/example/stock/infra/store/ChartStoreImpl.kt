package com.example.stock.infra.store

import com.example.stock.domain.ChartStore
import com.example.stock.domain.DailyChart
import com.example.stock.domain.SymbolMetaData
import com.example.stock.infra.repository.DailyChartRepository
import com.example.stock.infra.repository.SymbolMetaDataRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

@Component
class ChartStoreImpl(
    private val dailyChartRepository: DailyChartRepository,
    private val symbolMetaDataRepository: SymbolMetaDataRepository
) : ChartStore {

    override fun upsertDailyChart(dailyChart: DailyChart): Mono<DailyChart> {
        // utc 날짜 기준 dailyChart의 날짜가 오늘 이전인 경우와 오늘인 경우로 나누어 처리한다.
        return if (dailyChart.getUTCDate().isBefore(LocalDate.ofInstant(Instant.now(), ZoneOffset.UTC))) {
            dailyChartRepository.upsertBeforeTodayChart(dailyChart)
        } else {
            dailyChartRepository.upsertTodayChart(dailyChart)
        }
    }

    override fun saveSymbolMetaData(symbolMetaData: SymbolMetaData): Mono<SymbolMetaData> {
        return symbolMetaDataRepository.findBySymbol(symbolMetaData.symbol)
            .switchIfEmpty(symbolMetaDataRepository.save(symbolMetaData))
            .flatMap { Mono.empty() }
    }
}
