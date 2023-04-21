package com.example.stock.infra.repository

import com.example.stock.domain.DailyChart
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface DailyChartRepository : ReactiveMongoRepository<DailyChart, String>, DailyChartRepositoryCustom {

    fun findBySymbol(
        symbol: String,
        pageable: Pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "timestamp"))
    ): Flux<DailyChart>
}
