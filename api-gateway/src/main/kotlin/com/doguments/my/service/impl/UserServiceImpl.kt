package com.doguments.my.service.impl

import com.doguments.my.exception.LoginFailedException
import com.doguments.my.exception.UserInformationInvalidException
import com.doguments.my.model.User
import com.doguments.my.service.JWTService
import com.doguments.my.service.UserService
import com.doguments.my.service.user.GetByIdUserRequest
import com.doguments.my.service.user.GetByIdUserResponse
import com.doguments.my.service.user.LoginUserRequest
import com.doguments.my.service.user.LoginUserResponse
import com.doguments.my.service.user.RegisterUserRequest
import com.doguments.my.service.user.UserManagementServiceGrpcKt
import kotlinx.coroutines.runBlocking
import org.koin.core.component.inject
import kotlin.time.Duration.Companion.minutes

class UserServiceImpl: UserService {

    private val jwtService by inject<JWTService>()

    private val stub by inject<UserManagementServiceGrpcKt.UserManagementServiceCoroutineStub>()

    override fun register(login: String, email: String, password: String): Unit = runBlocking {
        val request = RegisterUserRequest.newBuilder()
            .setLogin(login)
            .setEmail(email)
            .setPassword(password)
            .build()

        val response = stub.registerUser(request)
        if (!response.success) {
            throw UserInformationInvalidException
        }
    }

    override fun login(login: String, password: String): String = runBlocking {
        val request = LoginUserRequest.newBuilder()
            .setLogin(login)
            .setPassword(password)
            .build()

        val response = stub.loginUser(request)
        val user = response.toUser()
        return@runBlocking if (response.success) {
            jwtService.generateJwtToken(user.id, 15.minutes)
        } else {
            throw LoginFailedException
        }
    }

    override fun getById(id: Long): User? = runBlocking {
        val request = GetByIdUserRequest.newBuilder()
            .setId(id)
            .build()

        val response = stub.getByIdUser(request)
        return@runBlocking if (response.success) {
            response.toUser()
        } else {
            null
        }
    }

    private fun GetByIdUserResponse.toUser(): User =
        User(
            id = id,
            login = login,
            email = email,
        )

    private fun LoginUserResponse.toUser(): User =
        User(
            id = id,
            login = login,
            email = email,
        )
}