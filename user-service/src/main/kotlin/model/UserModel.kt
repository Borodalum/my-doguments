package com.doguments.my.model

data class RegisterResult(
    val success: Boolean,
    val message: String,
)

data class LoginResult(
    val success: Boolean,
    val message: String,
)

data class GetByIdResult(
    val success: Boolean,
    val user: User?,
)

data class User(
    val id: Long,
    val login: String,
    val email: String,
    val password: String,
)