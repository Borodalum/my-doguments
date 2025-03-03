package com.doguments.my.di

import com.doguments.my.grpc.interceptor.UserManagementServiceInterceptor
import com.doguments.my.service.user.UserManagementServiceGrpcKt
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.ktor.server.application.ApplicationEnvironment
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

fun grpcModule(environment: ApplicationEnvironment) = module {
    single<ManagedChannel> {
        ManagedChannelBuilder.forAddress(
            environment.config.property("grpc.host").getString(),
            environment.config.property("grpc.port").getString().toInt(),
        ).usePlaintext()
            .enableRetry()
            .keepAliveTime(10, TimeUnit.SECONDS)
            .build()
    }

    single<UserManagementServiceInterceptor> { UserManagementServiceInterceptor() }

    single<UserManagementServiceGrpcKt.UserManagementServiceCoroutineStub> {
        UserManagementServiceGrpcKt
            .UserManagementServiceCoroutineStub(get<ManagedChannel>())
            .withInterceptors(get<UserManagementServiceInterceptor>())
    }
}