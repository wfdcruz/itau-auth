package com.itau.auth.application.validators

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.itau.auth.application.validators.impl.JWTValidatorImpl
import com.itau.auth.exceptions.JWTValidatorException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import reactor.test.StepVerifier

class JWTValidatorTest {
    companion object {
        @JvmStatic
        fun validTokenInput() = listOf(
            Arguments.of(JWT.decode("eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNzg0MSIsIk5hbWUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05sIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg")),
        )

        //TODO: remove duplicated inputs
        @JvmStatic
        fun assertionErrorTokenInput() = listOf(
            Arguments.of(JWT.decode("eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiRXh0ZXJuYWwiLCJTZWVkIjoiODgwMzciLCJOYW1lIjoiTTRyaWEgT2xpdmlhIn0.6YD73XWZYQSSMDf6H0i3-kylz1-TY_Yt6h1cV2Ku-Qs")),
            Arguments.of(JWT.decode("eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiTWVtYmVyIiwiT3JnIjoiQlIiLCJTZWVkIjoiMTQ2MjciLCJOYW1lIjoiVmFsZGlyIEFyYW5oYSJ9.cmrXV_Flm5mfdpfNUVopY_I2zeJUy4EZ4i3Fea98zvY")),
        )
    }

    @ParameterizedTest
    @MethodSource("validTokenInput")
    fun `should validate with success`(token: DecodedJWT) {
        StepVerifier.create(JWTValidatorImpl.validate(token)).consumeNextWith { assertTrue(it) }.verifyComplete()
    }

    @ParameterizedTest
    @MethodSource("assertionErrorTokenInput")
    fun `should validate with success and return false`(token: DecodedJWT) {
        StepVerifier.create(JWTValidatorImpl.validate(token))
            .consumeErrorWith {
            Assertions.assertInstanceOf(JWTValidatorException::class.java, it)
        }.verify()
    }
}