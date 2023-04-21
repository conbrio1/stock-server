package com.example.stock.infra.repository

import com.example.stock.domain.DailyChart
import reactor.core.publisher.Mono

interface DailyChartRepositoryCustom {
    /**
     * beforeTodayChart에 저장된 symbol과 timestamp를 기준으로 일치하는 데이터를 찾아 존재하지 않는 경우 저장한다.
     */
    fun upsertBeforeTodayChart(beforeTodayChart: DailyChart): Mono<DailyChart>

    /**
     * todayChart에 저장된 symbol과 timestamp를 기준으로 데이터를 찾아 존재하는 경우 update, 존재하지 않는 경우 insert
     */
    fun upsertTodayChart(todayChart: DailyChart): Mono<DailyChart>
}
