package com.itau.auth.application.decoder

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.itau.auth.exceptions.TokenDecoderException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
object TokenDecoder {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    fun decode(value: Any?): Mono<DecodedJWT> = Mono.defer {
        try {
            Mono.just(JWT.decode(value.toString()))
        } catch (ex: Throwable) {
            Mono.error(ex)
        }
    }.onErrorResume {
        logger.error("Invalid input")
        Mono.error(TokenDecoderException())
    }
}