plugins {
    kotlin("jvm")
    id("io.ktor.plugin")
    application
}

group = "com.doguments.my"
version = "0.0.1"

application {
    // Укажите главный класс вашего API Gateway (например, где инициализируется Ktor)
    mainClass.set("com.example.Application")
}

dependencies {
    // --- Ktor ---
    implementation("io.ktor:ktor-server-core:2.3.0")
    implementation("io.ktor:ktor-server-netty:2.3.0")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.0")
    implementation("io.ktor:ktor-serialization-gson:2.3.0")

    // --- Koin ---
    implementation("io.insert-koin:koin-ktor:3.4.3")
    implementation("io.insert-koin:koin-logger-slf4j:3.4.3")

    // --- SLF4J Logging с Logback ---
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("ch.qos.logback:logback-classic:1.4.5")

    // --- Тестирование ---
    testImplementation("io.ktor:ktor-server-tests:2.3.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:2.0.20")
}
