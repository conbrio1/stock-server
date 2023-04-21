package com.example.stock.infra.jackson

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.IOException
import java.time.Instant
import java.time.format.DateTimeFormatter

@Configuration
class ObjectMapperConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        return jacksonMapperBuilder()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .addModule(
                JavaTimeModule()
                    .addSerializer(
                        CustomInstantSerializer("yyyy-MM-dd'T'HH:mm:ssZ")
                    )
            )
            .build()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }
}

class CustomInstantSerializer(
    pattern: String
) : JsonSerializer<Instant>() {

    private val dateTimeFormatter = DateTimeFormatter.ofPattern(pattern)

    @Throws(IOException::class)
    override fun serialize(value: Instant, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeString(dateTimeFormatter.format(value))
    }
}
