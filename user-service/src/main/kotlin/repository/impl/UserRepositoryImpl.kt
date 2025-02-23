package com.doguments.my.repository.impl

import com.doguments.my.model.User
import com.doguments.my.model.table.Users
import com.doguments.my.repository.UserRepository
import com.doguments.my.repository.util.PasswordEncryptionUtil
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserRepositoryImpl(private val encryptionUtil: PasswordEncryptionUtil) : UserRepository {

    override suspend fun createAndGet(user: User): User = newSuspendedTransaction(Dispatchers.IO) {
        val encryptedPassword = encryptionUtil.encrypt(user.password)
        val generatedId = Users.insertAndGetId { row ->
            row[login] = user.login
            row[email] = user.email
            row[password] = encryptedPassword
        }
        user.copy(id = generatedId.value.toLong())
    }

    override suspend fun getById(id: Long): User = newSuspendedTransaction(Dispatchers.IO) {
        Users.select { Users.id eq id.toInt() }
            .map { it.toUser() }
            .singleOrNull() ?: throw NoSuchElementException("User with id $id not found")
    }

    override suspend fun getByLogin(login: String): User = newSuspendedTransaction(Dispatchers.IO) {
        Users.select { Users.login eq login }
            .map { it.toUser() }
            .singleOrNull() ?: throw NoSuchElementException("User with login $login not found")
    }

    private fun ResultRow.toUser(): User {
        val encryptedPassword = this[Users.password]
        val decryptedPassword = encryptionUtil.decrypt(encryptedPassword)
        return User(
            id = this[Users.id].value.toLong(),
            login = this[Users.login],
            email = this[Users.email],
            password = decryptedPassword
        )
    }
}