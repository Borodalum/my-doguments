package com.doguments.my.exception

import io.ktor.http.HttpStatusCode

abstract class ServerException(
    val code: HttpStatusCode,
    override val message: String,
) : RuntimeException()

object LoginFailedException : ServerException(HttpStatusCode.Unauthorized, "User login or password is incorrect") {
    private fun readResolve(): Any = LoginFailedException
}

object UserInformationInvalidException : ServerException(HttpStatusCode.Conflict, "User data is invalid") {
    private fun readResolve(): Any = UserInformationInvalidException
}
