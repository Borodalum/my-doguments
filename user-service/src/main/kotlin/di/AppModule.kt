package com.doguments.my.di

import com.doguments.my.repository.UserRepository
import com.doguments.my.repository.impl.UserRepositoryImpl
import com.doguments.my.service.UserService
import com.doguments.my.service.impl.UserServiceImpl
import org.koin.dsl.module

val appModule = module {
    single<UserService> { UserServiceImpl() }
    single<UserRepository> { UserRepositoryImpl(get()) }
}