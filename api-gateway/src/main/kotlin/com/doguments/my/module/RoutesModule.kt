package com.doguments.my.module

import com.doguments.my.route.authRoutes
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRoutes() {
    routing {
        authRoutes()
    }
}