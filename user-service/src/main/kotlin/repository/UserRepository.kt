package com.doguments.my.repository

import com.doguments.my.model.User

interface UserRepository {

    suspend fun createAndGet(user: User): User

    suspend fun getById(id: Long): User

    suspend fun getByLogin(login: String): User
}