package com.doguments.my.module

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.HoconApplicationConfig
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

fun configureDatabase(config: HoconApplicationConfig) {
    val dataSource = configureDataSource(config)

    Database.connect(dataSource)

    migrateDatabase(dataSource)
}

private fun migrateDatabase(dataSource: DataSource) {
    val flyway = Flyway.configure()
        .dataSource(dataSource)
        .load()
    flyway.info()
    flyway.migrate()
}

private fun configureDataSource(envConfig: HoconApplicationConfig): DataSource {
    val dbUrl = envConfig.property("db.url").getString()
    val dbUser = envConfig.property("db.user").getString()
    val dbPassword = envConfig.property("db.password").getString()

    val dbMaximumPoolSize = envConfig.property("db.maximum-pool-size").getString().toInt()

    val config = HikariConfig().apply {
        jdbcUrl = dbUrl
        username = dbUser
        password = dbPassword
        driverClassName = "org.postgresql.Driver"
        maximumPoolSize = dbMaximumPoolSize
    }

    return HikariDataSource(config)
}