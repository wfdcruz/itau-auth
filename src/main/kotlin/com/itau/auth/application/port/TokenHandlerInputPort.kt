package com.itau.auth.application.port

import reactor.core.publisher.Mono

interface TokenHandlerInputPort {
    fun process(token:Mono<String>): Mono<Boolean>
}