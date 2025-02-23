import com.google.protobuf.gradle.id

plugins {
    kotlin("jvm") version "2.0.20"
    application
    id("com.google.cloud.tools.jib")
    id("com.google.protobuf")
}

group = "com.doguments.my"
version = "0.0.1"

application {
    mainClass.set("com.doguments.my.ApplicationKt")
}

dependencies {
    // HikariCP для создания DataSource
    implementation("com.zaxxer:HikariCP:5.0.1")

    // ---Flyway---
    implementation("org.flywaydb:flyway-core:9.16.0")

    // --- Exposed ---
    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")

    implementation("org.jetbrains.exposed:exposed-java-time:0.41.1")

    implementation("org.postgresql:postgresql:42.5.4")

    // --- Koin ---
    implementation("io.insert-koin:koin-ktor:3.4.3")
    implementation("io.insert-koin:koin-logger-slf4j:3.4.3")

    // --- gRPC ---
    implementation("io.grpc:grpc-netty-shaded:1.53.0")
    implementation("io.grpc:grpc-protobuf:1.53.0")
    implementation("io.grpc:grpc-stub:1.53.0")
    implementation("io.grpc:grpc-kotlin-stub:1.3.0")

    // --- SLF4J Logging с Logback ---
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("ch.qos.logback:logback-classic:1.4.5")

    // --- Тестирование ---
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:2.0.20")
}

jib {
    from {
        image = "openjdk:17-jdk-slim"
    }
    to {
        image = "user-service:latest"
    }
    container {
        mainClass = "com.doguments.my.ApplicationKt"
        ports = listOf("50051")
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.23.4"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.53.0"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.3.0:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}