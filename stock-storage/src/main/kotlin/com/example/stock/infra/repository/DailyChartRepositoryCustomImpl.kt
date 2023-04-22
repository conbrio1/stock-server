package com.example.stock.infra.repository

import com.example.stock.domain.DailyChart
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

class DailyChartRepositoryCustomImpl(
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) : DailyChartRepositoryCustom {

    override fun upsertBeforeTodayChart(beforeTodayChart: DailyChart): Mono<DailyChart> {
        val query = symbolAndDateMatchQuery(beforeTodayChart)

        // query에 해당하는 데이터가 없어 insert하는 경우에만 값을 설정하도록 setOnInsert 사용
        val update = Update()
            .setOnInsert("high", beforeTodayChart.high)
            .setOnInsert("low", beforeTodayChart.low)
            .setOnInsert("open", beforeTodayChart.open)
            .setOnInsert("close", beforeTodayChart.close)
            .setOnInsert("volume", beforeTodayChart.volume)
            .setOnInsert("timestamp", beforeTodayChart.timestamp)

        // 새롭게 추가된 데이터가 없는 경우는 Mono.empty()를 반환
        return reactiveMongoTemplate.upsert(query, update, DailyChart::class.java)
            .flatMap { result ->
                if (result.wasAcknowledged() && result.modifiedCount > 0) {
                    Mono.just(beforeTodayChart)
                } else if (!result.wasAcknowledged()) {
                    Mono.error(RuntimeException("upsertBeforeTodayChart failed"))
                } else {
                    Mono.empty()
                }
            }
    }

    override fun upsertTodayChart(todayChart: DailyChart): Mono<DailyChart> {
        val query = symbolAndDateMatchQuery(todayChart)

        // update할 필드 설정
        val update = Update()
            .set("high", todayChart.high)
            .set("low", todayChart.low)
            .set("open", todayChart.open)
            .set("close", todayChart.close)
            .set("volume", todayChart.volume)
            .set("timestamp", todayChart.timestamp)

        // findAndModify를 사용하여 기존 document를 update하고, update된 document를 반환
        // 기존 document가 없는 경우 todayChart를 저장하고 저장된 document를 반환
        return reactiveMongoTemplate.findAndModify(
            query,
            update,
            FindAndModifyOptions().returnNew(true),
            DailyChart::class.java
        ).switchIfEmpty {
            reactiveMongoTemplate.save(todayChart)
        }
    }

    /**
     * symbol이 같고, timestamp가 todayChart와 UTC기준 동일한 날짜인 데이터 조회 query 생성
     */
    private fun symbolAndDateMatchQuery(beforeTodayChart: DailyChart) = Query(
        Criteria.where("symbol").`is`(beforeTodayChart.symbol)
            .andOperator(
                Criteria.where("timestamp").gte(beforeTodayChart.getUTCDate()),
                Criteria.where("timestamp").lt(
                    beforeTodayChart.getUTCDate().plusDays(1)
                )
            )
    )
}
