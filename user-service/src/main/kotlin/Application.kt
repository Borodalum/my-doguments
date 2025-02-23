package com.doguments.my

import com.doguments.my.di.appModule
import com.doguments.my.di.passwordEncryptionModule
import com.doguments.my.module.configureDatabase
import com.doguments.my.module.configureGrpcServer
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.HoconApplicationConfig
import org.koin.core.context.startKoin

fun main() {
    val config = HoconApplicationConfig(ConfigFactory.load())

    startKoin {
        modules(
            appModule,
            passwordEncryptionModule(config)
        )
    }

    configureDatabase(config)
    configureGrpcServer(config)
}
