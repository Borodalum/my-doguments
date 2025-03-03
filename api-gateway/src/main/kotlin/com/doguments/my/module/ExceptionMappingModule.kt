package com.doguments.my.module

import com.doguments.my.exception.ServerException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

fun Application.configureExceptionMapping() {
    install(StatusPages) {
        exception<ServerException> { call, cause ->
            call.respond(
                status = cause.code,
                message = cause.message,
            )
        }
        exception<Throwable> { call, _ ->
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = "Unexpected server error.",
            )
        }
    }
}