package com.example.stock.infra.lock

import com.mongodb.reactivestreams.client.MongoClient
import net.javacrumbs.shedlock.core.LockProvider
import net.javacrumbs.shedlock.provider.mongo.reactivestreams.ReactiveStreamsMongoLockProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SchedulerLockConfig {

    @Value("\${spring.data.mongodb.database}")
    lateinit var databaseName: String

    @Bean
    fun lockProvider(mongo: MongoClient): LockProvider {
        return ReactiveStreamsMongoLockProvider(mongo.getDatabase(databaseName))
    }
}
