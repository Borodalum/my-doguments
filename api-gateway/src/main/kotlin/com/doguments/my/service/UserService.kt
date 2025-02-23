package com.doguments.my.service

import com.doguments.my.model.User

interface UserService {

    fun register(login: String, email: String, password: String): String

    fun login(login: String, password: String, token: (User) -> String): String

    fun getById(id: Long): User?
}