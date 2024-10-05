package com.itau.auth.application.validators

import com.auth0.jwt.interfaces.DecodedJWT

interface JWTValidator {
    fun validate(token: DecodedJWT)
    fun enabled(): Boolean
}