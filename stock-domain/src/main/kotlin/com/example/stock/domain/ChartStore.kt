package com.example.stock.domain

import reactor.core.publisher.Mono

interface ChartStore {

    /**
     * 동일 timestamp에 해당하는 데이터가 존재하는 경우 update, 존재하지 않는 경우 insert한다.
     */
    fun upsertDailyChart(dailyChart: DailyChart): Mono<DailyChart>

    /**
     * symbol에 해당하는 SymbolMetaData를 저장한다.
     * symbol을 비교하여 이미 존재하는 경우는 저장하지 않으며 Mono.empty()를 반환한다.
     */
    fun saveSymbolMetaData(symbolMetaData: SymbolMetaData): Mono<SymbolMetaData>
}
