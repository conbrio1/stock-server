package com.example.stock.infra.repository

import com.example.stock.domain.SymbolMetaData
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface SymbolMetaDataRepository : ReactiveMongoRepository<SymbolMetaData, String> {
    fun findBySymbol(symbol: String): Mono<SymbolMetaData>
}
