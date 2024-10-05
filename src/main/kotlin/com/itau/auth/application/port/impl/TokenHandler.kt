package com.itau.auth.application.port.impl

import com.itau.auth.application.decoder.TokenDecoder
import com.itau.auth.application.port.TokenHandlerInputPort
import com.itau.auth.application.validators.impl.JWTValidatorImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class TokenHandler(
    private val tokenDecoder: TokenDecoder
) : TokenHandlerInputPort {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    //TODO: propagating span for tracing
    override fun process(token: Mono<String>): Mono<Boolean> =
        token
            .flatMap(tokenDecoder::decode)
            .flatMap(JWTValidatorImpl::validate)
            .doOnSuccess {
                //TODO: publish metrics
                logger.info("Token validated")
            }
            .doOnError {
                //TODO: publish metrics
                logger.error("Error to validate token")
            }
}
