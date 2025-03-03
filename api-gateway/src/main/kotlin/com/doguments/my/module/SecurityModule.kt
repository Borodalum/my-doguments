package com.doguments.my.module

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.doguments.my.service.UserService
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {
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
                    userService.getById(id)
                }
            }
        }
    }
}