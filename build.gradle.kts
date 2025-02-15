plugins {
    kotlin("jvm") version "2.0.20" apply false
    id("io.ktor.plugin") version "2.3.0" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}