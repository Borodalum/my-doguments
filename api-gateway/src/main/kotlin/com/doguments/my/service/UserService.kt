package com.doguments.my.service

import com.doguments.my.model.User
import org.koin.core.component.KoinComponent

interface UserService : KoinComponent {

    fun register(login: String, email: String, password: String)

    fun login(login: String, password: String): String

    fun getById(id: Long): User?
}