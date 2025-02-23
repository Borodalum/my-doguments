package com.doguments.my.service.impl

import com.doguments.my.model.User
import com.doguments.my.service.UserService
import com.doguments.my.service.user.GetByIdUserRequest
import com.doguments.my.service.user.GetByIdUserResponse
import com.doguments.my.service.user.LoginUserRequest
import com.doguments.my.service.user.LoginUserResponse
import com.doguments.my.service.user.RegisterUserRequest
import com.doguments.my.service.user.UserManagementServiceGrpcKt
import io.grpc.ManagedChannel
import kotlinx.coroutines.runBlocking

class UserServiceImpl(
    channel: ManagedChannel,
) : UserService {

    private val stub = UserManagementServiceGrpcKt.UserManagementServiceCoroutineStub(channel)

    override fun register(login: String, email: String, password: String): String = runBlocking {
        val request = RegisterUserRequest.newBuilder()
            .setLogin(login)
            .setEmail(email)
            .setPassword(password)
            .build()

        val response = stub.registerUser(request)

        return@runBlocking if (response.success) {
            "Registration successful: ${response.message}"
        } else {
            "Registration failed: ${response.message}"
        }
    }

    override fun login(login: String, password: String, token: (User) -> String): String = runBlocking {
        val request = LoginUserRequest.newBuilder()
            .setLogin(login)
            .setPassword(password)
            .build()

        val response = stub.loginUser(request)
        val user = response.toUser()
        return@runBlocking if (response.success) {
            println(user.id)
            token(user)
        } else {
            "Login failed: ${response.message}"
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