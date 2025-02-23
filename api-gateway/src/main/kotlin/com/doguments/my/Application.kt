package com.doguments.my

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.doguments.my.di.appModule
import com.doguments.my.route.authRoutes
import com.doguments.my.service.UserService
import io.ktor.serialization.gson.gson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.netty.EngineMain
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    val userService by inject<UserService>()
    val hmacSecret = environment.config.property("jwt.secret").getString()
    install(Authentication) {
        jwt {
            verifier(
                JWT
                    .require(Algorithm.HMAC256(hmacSecret))
                    .build()
            )
            validate {
                it.payload.getClaim("id").asLong()?.let { id ->
                    println("Id is $id")
                    userService.getById(id)
                }
            }
        }
    }

    install(Koin) {
        modules(appModule(environment))
    }

    routing {
        authRoutes()
    }
}