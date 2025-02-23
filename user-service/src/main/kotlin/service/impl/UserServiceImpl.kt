package com.doguments.my.service.impl

import com.doguments.my.model.GetByIdResult
import com.doguments.my.model.LoginResult
import com.doguments.my.model.RegisterResult
import com.doguments.my.model.User
import com.doguments.my.repository.UserRepository
import com.doguments.my.service.UserService
import kotlinx.coroutines.runBlocking
import org.koin.core.component.inject

class UserServiceImpl : UserService {
    private val userRepository by inject<UserRepository>()

    override fun register(login: String, email: String, password: String): RegisterResult = runBlocking {
        val user = User(
            id = 0,
            login = login,
            email = email,
            password = password,
        )
        try {
            userRepository.createAndGet(user)
            return@runBlocking RegisterResult(true, "User registred")
        } catch (e: Exception) {
            return@runBlocking RegisterResult(false, "User registration failed: ${e.message}")
        }
    }

    override fun login(login: String, password: String): LoginResult = runBlocking {
        try {
            val user = userRepository.getByLogin(login)
            return@runBlocking if (user.password == password) {
                LoginResult(true, "User singed in.")
            } else {
                LoginResult(false, "User login or password is incorrect.")
            }
        } catch (e: Exception) {
            return@runBlocking LoginResult(false, "User login or password is incorrect.")
        }
    }

    override fun getById(id: Long): GetByIdResult = runBlocking {
        try {
            val user = userRepository.getById(id)
            println("user $user")
            return@runBlocking GetByIdResult(true, user)
        } catch (e: Exception) {
            println(e.message)
            return@runBlocking GetByIdResult(false, null)
        }
    }
}