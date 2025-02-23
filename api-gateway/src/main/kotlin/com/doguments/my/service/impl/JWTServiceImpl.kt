package com.doguments.my.service.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.doguments.my.service.JWTService
import java.time.Instant
import kotlin.time.Duration
import kotlin.time.toJavaDuration

class JWTServiceImpl(
    private val secret: String,
) : JWTService {

    override fun generateJwtToken(userId: Long, duration: Duration): String {
        return JWT.create()
            .withClaim("id", userId)
            .withExpiresAt(Instant.now() + duration.toJavaDuration())
            .sign(Algorithm.HMAC256(secret))
    }
}