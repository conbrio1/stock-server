package com.example.stock.presentation.swagger

import com.example.stock.presentation.response.ChartResponse
import com.example.stock.presentation.swagger.ResponseExample.DAILY_CHART_RESPONSE
import com.example.stock.presentation.swagger.ResponseExample.INVALID_DAYS_RESPONSE
import com.example.stock.presentation.swagger.ResponseExample.SYMBOL_NOT_FOUND_RESPONSE
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.MediaType

@Operation(
    summary = "일별 차트 조회",
    description = "일별 차트 정보를 조회한다."
)
@Parameters(
    value = [
        Parameter(
            name = "symbol",
            `in` = ParameterIn.PATH,
            description = "종목 코드. 전달받은 종목 코드 정보를 기준으로 일별 차트 정보를 조회한다.\n\n" +
                "현재 삼성(**005930.KS**)만 조회 가능하다.",
            required = true,
            example = "005930.KS"
        ),
        Parameter(
            name = "days",
            `in` = ParameterIn.QUERY,
            required = false,
            description = "일자 수. 1일치 또는 5일치 데이터만 조회 가능하다.",
        )
    ]
)
@ApiResponses(
    value = [
        ApiResponse(
            responseCode = "200",
            description = "일별 차트 조회 성공",
            content = [
                Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = [
                        ExampleObject(
                            name = "OK",
                            value = DAILY_CHART_RESPONSE
                        )
                    ],
                    schema = Schema(
                        implementation = ChartResponse.DailyChartInfo::class
                    )
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "요청받은 일자 수가 유효하지 않음",
            content = [
                Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = [
                        ExampleObject(
                            name = "BAD_REQUEST",
                            value = INVALID_DAYS_RESPONSE
                        )
                    ]
                )
            ]
        ),
        ApiResponse(
            responseCode = "404",
            description = "요청받은 종목 코드가 존재하지 않음",
            content = [
                Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = [
                        ExampleObject(
                            name = "NOT_FOUND",
                            value = SYMBOL_NOT_FOUND_RESPONSE
                        )
                    ]
                )
            ]
        )
    ]
)
annotation class GetDailyChartSwaggerMeta
