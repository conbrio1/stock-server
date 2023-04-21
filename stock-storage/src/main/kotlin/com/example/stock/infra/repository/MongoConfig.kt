package com.example.stock.infra.repository

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@EnableMongoRepositories(basePackages = ["com.example.stock.infra.repository"])
class MongoConfig
