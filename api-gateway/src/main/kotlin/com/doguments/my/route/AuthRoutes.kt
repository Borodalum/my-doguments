package com.doguments.my.route

import com.doguments.my.model.LoginRequest
import com.doguments.my.model.RegisterRequest
import com.doguments.my.model.User
import com.doguments.my.service.JWTService
import com.doguments.my.service.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject
import kotlin.time.Duration.Companion.minutes

fun Route.authRoutes() {
    val userService by inject<UserService>()
    val jwtService by inject<JWTService>()

    route("/auth") {
        post("/register") {
            val request = call.receive<RegisterRequest>()
            val result = userService.register(request.login, request.email, request.password)
            call.respond(
                HttpStatusCode.OK,
                mapOf(
                    "message" to result,
                ),
            )
        }

        post("/login") {
            val request = call.receive<LoginRequest>()
            val token = userService.login(request.login, request.password) { user: User ->
                jwtService.generateJwtToken(user.id, 15.minutes)
            }
            call.respond(HttpStatusCode.OK, mapOf("token" to token))
        }

        authenticate {
            post("/say/hello") {
                println("User says hello")
                call.respond(HttpStatusCode.OK, mapOf("ok" to "Helllooo brother!"))
            }
        }
    }
}