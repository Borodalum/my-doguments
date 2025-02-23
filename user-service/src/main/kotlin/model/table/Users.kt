package com.doguments.my.model.table

import org.jetbrains.exposed.dao.id.IntIdTable

object Users : IntIdTable("users") {
    val login = varchar("login", 128)
    val email = varchar("email", 128)
    val password = varchar("password", 128)
}