package com.doguments.my.model

data class RegisterRequest(val login: String, val email: String, val password: String)
data class LoginRequest(val login: String, val password: String)