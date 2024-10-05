package com.itau.auth.application.validators.impl

import com.auth0.jwt.interfaces.DecodedJWT
import com.itau.auth.exceptions.JWTValidatorException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono

object JWTValidatorImpl {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    //TODO: implement smart way to pass these parameters to be more testable
    private val validators = setOf(
        ClaimSizeValidator,
        ClaimKeysValidator,
        ClaimNameValidator,
        ClaimSeedValidator,
        ClaimRoleValidator
    )

    fun validate(token: DecodedJWT): Mono<Boolean> {
        try {
            validators.forEach {
                if (it.enabled()) {
                    it.validate(token)
                }
            }
            return Mono.just(true)
        } catch (ex: Throwable) {
            //TODO: Create a way to identify each step of validation to create metric accord each failure validation
            logger.error("Validation error", ex)
            return Mono.error(JWTValidatorException())
        }
    }
}