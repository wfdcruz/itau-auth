package com.itau.auth.application.validators.impl

import com.auth0.jwt.interfaces.Claim
import com.auth0.jwt.interfaces.DecodedJWT
import com.itau.auth.application.validators.JWTValidator

object ClaimSizeValidator : JWTValidator {
    override fun validate(token: DecodedJWT) =
        assert(token.claims.size == 3)

    override fun enabled(): Boolean = true
}

object ClaimKeysValidator : JWTValidator {

    private val claims = listOf("Role", "Seed", "Name")
    override fun validate(token: DecodedJWT) = claims.forEach {
        assert(token.claims.containsKey(it))
    }

    override fun enabled(): Boolean = true
}

object ClaimNameValidator : JWTValidator {
    override fun validate(token: DecodedJWT) =
        with(token.getClaim("Name")) {
            assert(this.isNotNullOrBlank() && this.hasLessThen(256) && !this.hasNumbers())
        }

    override fun enabled(): Boolean = true
}

object ClaimSeedValidator : JWTValidator {
    override fun validate(token: DecodedJWT) =
        with(token.getClaim("Seed")) {
            assert(this.isNotNullOrBlank() && this.isOdd())
        }

    override fun enabled(): Boolean = true
}

object ClaimRoleValidator : JWTValidator {

    private val roles = listOf("Admin", "Member", "External")
    override fun validate(token: DecodedJWT) =
        with(token.getClaim("Role")) {
            assert(this.isNotNullOrBlank() && roles.contains(this.asString()))
        }

    override fun enabled(): Boolean = true
}

private fun Claim.isOdd() = this.asString().toInt() % 2 != 0
private fun Claim.isNotNullOrBlank() = !this.isNull &&
    this.asString().isNotBlank()

private fun Claim.hasLessThen(max: Int) = this.asString().length <= max
private fun Claim.hasNumbers() = this.asString().any { it.isDigit() }