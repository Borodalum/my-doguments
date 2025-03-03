package com.doguments.my.grpc.interceptor

import io.grpc.CallOptions
import io.grpc.Channel
import io.grpc.ClientCall
import io.grpc.ClientInterceptor
import io.grpc.ForwardingClientCall
import io.grpc.ForwardingClientCallListener
import io.grpc.Metadata
import io.grpc.MethodDescriptor
import io.grpc.Status

class UserManagementServiceInterceptor : ClientInterceptor {
    override fun <ReqT, RespT> interceptCall(
        method: MethodDescriptor<ReqT, RespT>,
        callOptions: CallOptions,
        next: Channel
    ): ClientCall<ReqT, RespT> {

        val originalCall = next.newCall(method, callOptions)

        return object : ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(originalCall) {
            override fun start(responseListener: Listener<RespT>, headers: Metadata) {
                val wrappedListener =
                    object : ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
                        override fun onClose(status: Status, trailers: Metadata) {
                            // todo
                            super.onClose(status, trailers)
                        }
                    }

                super.start(wrappedListener, headers)
            }
        }
    }
}
