package com.doguments.my.di

import com.doguments.my.service.JWTService
import com.doguments.my.service.UserService
import com.doguments.my.service.impl.JWTServiceImpl
import com.doguments.my.service.impl.UserServiceImpl
import io.ktor.server.application.ApplicationEnvironment
import org.koin.dsl.module

fun appModule(environment: ApplicationEnvironment) = module {
    single<JWTService> {
        JWTServiceImpl(environment.config.property("jwt.secret").getString())
    }

    single<UserService> { UserServiceImpl() }
}