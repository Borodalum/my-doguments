package com.doguments.my.di

import com.doguments.my.repository.util.PasswordEncryptionUtil
import io.ktor.server.config.HoconApplicationConfig
import org.koin.dsl.module

fun passwordEncryptionModule(config: HoconApplicationConfig) = module {
    single {
        val secret = config.property("encryption.secret").getString()
        PasswordEncryptionUtil(secret)
    }
}