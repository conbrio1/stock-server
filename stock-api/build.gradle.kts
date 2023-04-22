plugins {
    id("com.google.cloud.tools.jib")
}

dependencies {
    // sub modules
    implementation(project(":stock-domain"))
    runtimeOnly(project(":stock-storage"))
    implementation(project(":stock-external-client"))
    // spring webflux
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    // reactive redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    // validation
    implementation("org.springframework.boot:spring-boot-starter-validation")
    // open api
    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.7.0")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.7.0")
    // scheduler lock
    implementation("net.javacrumbs.shedlock:shedlock-spring:5.2.0")
    implementation("net.javacrumbs.shedlock:shedlock-provider-mongo-reactivestreams:5.2.0")
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

jib {
    from {
        image = "amazoncorretto:17"
        platforms {
            platform {
                architecture = "amd64"
                os = "linux"
            }
        }
    }
    to {
        image = System.getenv("REGISTRY_URL") + "/" + System.getenv("REPOSITORY_NAME")
        tags = mutableSetOf(System.getenv("IMAGE_TAG") ?: "latest")
        auth {
            username = System.getenv("REGISTRY_USERNAME") ?: ""
            password = System.getenv("REGISTRY_PASSWORD") ?: ""
        }
    }
    container {
        mainClass = "com.example.stock.StockApiApplicationKt"
        environment = mapOf(
            "MONGODB_HOST" to System.getenv("MONGODB_HOST"),
            "MONGODB_PORT" to System.getenv("MONGODB_PORT"),
            "MONGODB_USERNAME" to System.getenv("MONGODB_USERNAME"),
            "MONGODB_PASSWORD" to System.getenv("MONGODB_PASSWORD")
        )
        jvmFlags = mutableListOf(
            "-Dspring.profiles.active=" + System.getenv("PROFILE")
        )
    }
}
