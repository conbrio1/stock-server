package com.example.stock.presentation.response

import com.example.stock.domain.DailyChart
import com.example.stock.domain.SymbolMetaData

object ChartResponse {

    data class DailyChartInfo(
        val metadata: SymbolMetaData,
        val quotes: List<DailyChart>
    )
}
