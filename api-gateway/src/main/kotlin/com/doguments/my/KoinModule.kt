package com.doguments.my

import com.doguments.my.di.appModule
import com.doguments.my.di.grpcModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(appModule(environment), grpcModule(environment))
    }
}