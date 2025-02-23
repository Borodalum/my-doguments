package com.doguments.my.di

import com.doguments.my.service.JWTService
import com.doguments.my.service.UserService
import com.doguments.my.service.impl.JWTServiceImpl
import com.doguments.my.service.impl.UserServiceImpl
import io.grpc.CallOptions
import io.grpc.Channel
import io.grpc.ClientCall
import io.grpc.ClientInterceptor
import io.grpc.ForwardingClientCall
import io.grpc.ForwardingClientCallListener
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.Metadata
import io.grpc.MethodDescriptor
import io.grpc.Status
import io.ktor.server.application.ApplicationEnvironment
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

fun appModule(environment: ApplicationEnvironment) = module {
    single<ManagedChannel> {
        ManagedChannelBuilder.forAddress(
            environment.config.property("grpc.host").getString(),
            environment.config.property("grpc.port").getString().toInt(),
        ).usePlaintext()
            .enableRetry()
            .keepAliveTime(10, TimeUnit.SECONDS)
            .intercept(LoggingClientInterceptor())
            .build()
    }

    single<JWTService> {
        JWTServiceImpl(environment.config.property("jwt.secret").getString())
    }

    single<UserService> { UserServiceImpl(get()) }
}

class LoggingClientInterceptor : ClientInterceptor {
    override fun <ReqT, RespT> interceptCall(
        method: MethodDescriptor<ReqT, RespT>,
        callOptions: CallOptions,
        next: Channel
    ): ClientCall<ReqT, RespT> {
        println("Начало RPC вызова: ${method.fullMethodName}")
        val clientCall = next.newCall(method, callOptions)
        return object : ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(clientCall) {
            override fun start(responseListener: Listener<RespT>, headers: Metadata) {
                super.start(object :
                    ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
                    override fun onClose(status: Status, trailers: Metadata) {
                        println("RPC вызов ${method.fullMethodName} завершён со статусом: ${status.code} (${status.description ?: "нет описания"})")
                        super.onClose(status, trailers)
                    }
                }, headers)
            }
        }
    }
}
