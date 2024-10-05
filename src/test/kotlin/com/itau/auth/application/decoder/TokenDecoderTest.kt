package com.itau.auth.application.decoder

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import reactor.test.StepVerifier

class TokenDecoderTest {
    companion object {

        @JvmStatic
        fun validTokenInput() = listOf(
            Arguments.of("eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNzg0MSIsIk5hbWUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05sIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg"),
            Arguments.of("eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiRXh0ZXJuYWwiLCJTZWVkIjoiODgwMzciLCJOYW1lIjoiTTRyaWEgT2xpdmlhIn0.6YD73XWZYQSSMDf6H0i3-kylz1-TY_Yt6h1cV2Ku-Qs"),
            Arguments.of("eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiTWVtYmVyIiwiT3JnIjoiQlIiLCJTZWVkIjoiMTQ2MjciLCJOYW1lIjoiVmFsZGlyIEFyYW5oYSJ9.cmrXV_Flm5mfdpfNUVopY_I2zeJUy4EZ4i3Fea98zvY"),
        )

        //TODO: remove duplicated inputs
        @JvmStatic
        fun nonJWTInput() = listOf(
            Arguments.of("eyJhbGciOiJzI1NiJ9.dfsdfsfryJSr2xrIjoiQWRtaW4iLCJTZrkIjoiNzg0MSIsIk5hbrUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05fsdfsIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg"),
            Arguments.of(""),
            Arguments.of(123),
            Arguments.of(null),
        )
    }

    @ParameterizedTest
    @MethodSource("validTokenInput")
    fun `should decode with success`(token: Any?) {
        StepVerifier.create(TokenDecoder.decode(token))
            .consumeNextWith {}
            .verifyComplete()
    }
    @ParameterizedTest
    @MethodSource("nonJWTInput")
    fun `should return TokenDecoderException`(token: Any?) {
        StepVerifier.create(TokenDecoder.decode(token))
            .expectError()
            .verify()
    }
}