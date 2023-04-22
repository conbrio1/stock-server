# 주식 정보 수집, 조회 application

## 기능

### 주식 정보 수집

`https://query1.finance.yahoo.com/v8/finance/chart/{symbol}?interval=1d&range=5d`
위 api를 호출하여 5일치 일일 주식 정보를 수집하고, 저장합니다. (*삼성전자 주식만 수집합니다.*)

### 주식 정보 조회

- 주식 정보 조회
    - `GET /daily-chart/{symbol}`
        - path parameter
            - `symbol`: 조회할 주식의 symbol
        - query parameter
            - days: 가장 최근 날짜의 정보부터 몇일치의 주식 정보를 조회할지 지정합니다. (default: 5)
        - `symbol`에 해당하는 주식 정보를 조회합니다.

자세한 api 명세는 application 실행 후 `http://localhost:8080/` 에서 확인할 수 있습니다.

## DB 설계

mongodb를 사용하여 다음과 같이 설계하였습니다.

- database: `stock`
    - collection: `daily_chart`
        - `symbol`: 주식 symbol
        - `date`: 날짜
        - `open`: 시가
        - `high`: 고가
        - `low`: 저가
        - `close`: 종가
        - `volume`: 거래량
    - collection: `symbol_metadata`
        - `_id`: 주식 symbol
        - `currency`: 통화
        - `instrumentType`: 주식 종류
        - `exchangeName`: 거래소 이름
        - `exchangeTimezoneName`: 거래소 시간대 이름

## 모듈

- `stock-server`
    - project의 root module
    - 하위 module들을 포함합니다.
- `stock-api`
    - runnable한 module로써 api를 제공합니다.
    - 다른 module에 대한 의존성은 다음과 같습니다.
        - `stock-domain`: *implementation*
        - `stock-storage`: *runtimeOnly*
        - `stock-external-client`: *implementation*
- `stock-domain`
    - domain을 정의합니다.
- `stock-storage`
    - database와의 연동을 처리합니다.
    - `stock-domain`을 *compileOnly*로 의존합니다.
- `stock-external-client`
    - 외부 주식 정보 api와의 통신을 처리합니다.

## 사용 기술

- **build tool**
    - gradle 7.6.1
- **language**
    - kotlin 1.6.21
- **framework & library**
    - spring boot 2.7.10
    - spring webflux
    - spring data mongodb
    - shedlock 5.2.0
- **database**
    - mongoDB 5.0.16 community
    - *(TBD)* redis(cache)

## 실행 방법

- database 실행(docker-compose)
    - `docker-compose -f ./docker-compose/docker-compose.yaml up -d`
- application 실행
    - `./gradlew :stock-api:bootRun`

## Jib를 사용한 docker image build & push

- 환경 변수 설정
  - `REGISTRY_URL`: docker registry url (ex. `registry.hub.docker.com`)
  - `REPOSITORY_NAME`: docker repository name (ex. `my-repository`)
  - `IMAGE_TAG`: docker image tag (ex. `latest`)
  - `REGISTRY_USERNAME`: docker registry username
  - `REGISTRY_PASSWORD`: docker registry password
  - `MONGODB_HOST`: mongodb host
  - `MONGODB_PORT`: mongodb port
  - `MONGODB_USERNAME`: mongodb username
  - `MONGODB_PASSWORD`: mongodb password
  - `PROFILE`: `spring.profiles.active`에 설정할 값

`REGISTRY_USERNAME`과 `REGISTRY_PASSWORD`는 사용하는 docker registry나 인증 방식에 따라 설정을 수정해야 할 수 있습니다.
`stock-api/src/main/resources/application.yaml`과 `stock-storage/src/main/resources/application-storage.yaml` 파일을 참고하세요.
