plugins {
    kotlin("jvm") version "2.0.20" apply false
    id("io.ktor.plugin") version "2.3.0" apply false
    id("com.google.cloud.tools.jib") version "3.3.1" apply false
    id("com.google.protobuf") version "0.9.2" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}