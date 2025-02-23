package com.doguments.my.service

import kotlin.time.Duration

interface JWTService {

    fun generateJwtToken(userId: Long, duration: Duration): String
}