package com.example.stock.infra.client

import com.example.stock.infra.client.dto.YahooChartData
import com.example.stock.infra.client.enums.ApiValidRange
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class FinanceApiClientImpl(
    webClientBuilder: WebClient.Builder,
    @Value("\${finance-api.base-url}")
    private val apiUrl: String,
    @Value("\${finance-api.path}")
    private val apiPath: String,
    @Value("\${finance-api.interval}")
    private val interval: String
) : FinanceApiClient {

    private val webClient: WebClient

    init {
        webClient = webClientBuilder.baseUrl(apiUrl)
            .build()
    }

    companion object {
        const val INTERVAL = "interval"
        const val RANGE = "range"
    }

    override fun getDailyChartDataBySymbol(symbol: String, range: ApiValidRange): Mono<YahooChartData> {
        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path(apiPath + "/$symbol")
                    .queryParam(INTERVAL, interval)
                    .queryParam(RANGE, range.apiParamValue)
                    .build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(YahooChartData::class.java)
    }
}
