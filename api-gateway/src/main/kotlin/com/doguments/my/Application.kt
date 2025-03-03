package com.doguments.my

import com.doguments.my.module.configureExceptionMapping
import com.doguments.my.module.configureRoutes
import com.doguments.my.module.configureSecurity
import com.doguments.my.module.configureSerialization
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    configureKoin()
    configureSerialization()
    configureExceptionMapping()
    configureSecurity()
    configureRoutes()
}