package com.example.stock.presentation.swagger

object ResponseExample {
    const val DAILY_CHART_RESPONSE = """
        {
            "status": 200,
            "code": "OK",
            "data": {
                "metadata": {
                    "symbol": "005930.KS",
                    "currency": "KRW",
                    "instrumentType": "EQUITY",
                    "exchangeName": "KSC",
                    "exchangeTimezoneName": "Asia/Seoul"
                },
                "quotes": [
                    {
                        "high": 65900.0,
                        "low": 65400.0,
                        "open": 65800.0,
                        "close": 65700.0,
                        "volume": 10538622,
                        "timestamp": "2023-04-21T00:00:00Z"
                    },
                    {
                        "high": 65900.0,
                        "low": 65400.0,
                        "open": 65800.0,
                        "close": 65700.0,
                        "volume": 10538622,
                        "timestamp": "2023-04-21T00:00:00Z"
                    },
                    {
                        "high": 65300.0,
                        "low": 64600.0,
                        "open": 65100.0,
                        "close": 65300.0,
                        "volume": 9501169,
                        "timestamp": "2023-04-20T00:00:00Z"
                    },
                    {
                        "high": 65800.0,
                        "low": 65300.0,
                        "open": 65500.0,
                        "close": 65500.0,
                        "volume": 10255985,
                        "timestamp": "2023-04-19T00:00:00Z"
                    },
                    {
                        "high": 66000.0,
                        "low": 64800.0,
                        "open": 65900.0,
                        "close": 65600.0,
                        "volume": 14802060,
                        "timestamp": "2023-04-18T00:00:00Z"
                    }
                ]
            }
        }
    """

    const val INVALID_DAYS_RESPONSE = """
        {
            "status": 400,
            "code": "INVALID_DAYS",
            "data": "유효하지 않은 days 파라미터에 대한 요청입니다."
        }
    """

    const val SYMBOL_NOT_FOUND_RESPONSE = """
        {
            "status": 404,
            "code": "SYMBOL_NOT_FOUND",
            "data": "요청받은 종목 코드가 존재하지 않습니다."
        }
    """
}
