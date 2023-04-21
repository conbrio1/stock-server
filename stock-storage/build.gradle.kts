dependencies {
    compileOnly(project(":stock-domain"))
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
}
