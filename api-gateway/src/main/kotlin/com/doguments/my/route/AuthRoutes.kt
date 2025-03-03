package com.doguments.my.route

import com.doguments.my.model.LoginRequest
import com.doguments.my.model.RegisterRequest
import com.doguments.my.service.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.authRoutes() {
    val userService by inject<UserService>()

    route("/auth") {
        post("/register") {
            val request = call.receive<RegisterRequest>()
            userService.register(request.login, request.email, request.password)
            call.respond(HttpStatusCode.OK)
        }

        post("/login") {
            val request = call.receive<LoginRequest>()
            val token = userService.login(request.login, request.password)
            call.response.header("Authorization", token)
            call.respond(HttpStatusCode.OK)
        }

        authenticate {
            post("/say/hello") {
                call.respond(HttpStatusCode.OK, mapOf("ok" to "Helllooo brother!"))
            }
        }
    }
}