import com.google.protobuf.gradle.id

plugins {
    kotlin("jvm")
    id("io.ktor.plugin")
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
    // --- Ktor ---
    implementation("io.ktor:ktor-server-core:2.3.0")
    implementation("io.ktor:ktor-server-netty:2.3.0")
    implementation("io.ktor:ktor-server-auth:2.3.0")
    implementation("io.ktor:ktor-server-auth-jwt:2.3.0")
    implementation("io.ktor:ktor-server-status-pages:2.3.0")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.0")
    implementation("io.ktor:ktor-serialization-gson:2.3.0")

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
    testImplementation("io.ktor:ktor-server-tests:2.3.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:2.0.20")
}

jib {
    from {
        image = "openjdk:17-jdk-slim"
    }
    to {
        image = "api-gateway:latest"
    }
    container {
        mainClass = "com.doguments.my.ApplicationKt"
        ports = listOf("8080")
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

