package com.doguments.my.module

import com.doguments.my.grpc.UserServiceGrpcImpl
import io.grpc.ForwardingServerCallListener.SimpleForwardingServerCallListener
import io.grpc.Metadata
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.ServerCall
import io.grpc.ServerCallHandler
import io.grpc.ServerInterceptor
import io.ktor.server.config.HoconApplicationConfig

fun configureGrpcServer(config: HoconApplicationConfig) {
    val port = config.property("grpc.port").getString().toInt()

    val server: Server = ServerBuilder.forPort(port)
        .addService(UserServiceGrpcImpl())
        .intercept(LoggingServerInterceptor())
        .build()
        .start()

    server.awaitTermination()
}

class LoggingServerInterceptor : ServerInterceptor {
    override fun <ReqT, RespT> interceptCall(
        call: ServerCall<ReqT, RespT>,
        headers: Metadata,
        next: ServerCallHandler<ReqT, RespT>
    ): ServerCall.Listener<ReqT> {
        println("Получен RPC вызов: ${call.methodDescriptor.fullMethodName}")
        val listener = next.startCall(call, headers)
        return object : SimpleForwardingServerCallListener<ReqT>(listener) {

        }
    }
}