package com.doguments.my.model

import io.ktor.server.auth.Principal

data class User(
    val id: Long,
    val login: String,
    val email: String,
) : Principal