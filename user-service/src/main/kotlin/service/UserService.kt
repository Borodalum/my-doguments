package com.doguments.my.service

import com.doguments.my.model.GetByIdResult
import com.doguments.my.model.LoginResult
import com.doguments.my.model.RegisterResult
import org.koin.core.component.KoinComponent

interface UserService : KoinComponent {

    fun register(login: String, email: String, password: String): RegisterResult

    fun login(login: String, password: String): LoginResult

    fun getById(id: Long): GetByIdResult
}