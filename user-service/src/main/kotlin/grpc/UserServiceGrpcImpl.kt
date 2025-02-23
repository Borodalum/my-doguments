package com.doguments.my.grpc

import com.doguments.my.service.UserService
import com.doguments.my.service.user.GetByIdUserRequest
import com.doguments.my.service.user.GetByIdUserResponse
import com.doguments.my.service.user.LoginUserRequest
import com.doguments.my.service.user.LoginUserResponse
import com.doguments.my.service.user.RegisterUserRequest
import com.doguments.my.service.user.RegisterUserResponse
import com.doguments.my.service.user.UserManagementServiceGrpcKt
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserServiceGrpcImpl : KoinComponent, UserManagementServiceGrpcKt.UserManagementServiceCoroutineImplBase() {

    private val userService by inject<UserService>()

    override suspend fun registerUser(request: RegisterUserRequest): RegisterUserResponse {
        val (success, message) = userService.register(request.login, request.email, request.password)
        return RegisterUserResponse.newBuilder()
            .setSuccess(success)
            .setMessage(message)
            .build()
    }

    override suspend fun loginUser(request: LoginUserRequest): LoginUserResponse {
        val (success, message) = userService.login(request.login, request.password)
        return LoginUserResponse.newBuilder()
            .setSuccess(success)
            .setMessage(message)
            .build()
    }

    override suspend fun getByIdUser(request: GetByIdUserRequest): GetByIdUserResponse {
        val (success, user) = userService.getById(request.id)

        user ?: return GetByIdUserResponse.newBuilder().build()
        
        return GetByIdUserResponse.newBuilder()
            .setSuccess(success)
            .setId(user.id)
            .setLogin(user.login)
            .setEmail(user.email)
            .build()
    }
}