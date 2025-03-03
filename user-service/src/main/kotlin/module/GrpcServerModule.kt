package com.doguments.my.module

import com.doguments.my.grpc.UserServiceGrpcImpl
import io.grpc.Server
import io.grpc.ServerBuilder
import io.ktor.server.config.HoconApplicationConfig

fun configureGrpcServer(config: HoconApplicationConfig) {
    val port = config.property("grpc.port").getString().toInt()

    val server: Server = ServerBuilder.forPort(port)
        .addService(UserServiceGrpcImpl())
        .build()
        .start()

    server.awaitTermination()
}
