package com.farahaty.restapi.security

import com.farahaty.restapi.add
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ClaimsMutator
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.Authentication
import java.util.*
import kotlin.collections.HashMap


class JWTUtil(
    private val securityProperties: SecurityProperties
) {


    fun generateToken(userAccount: UserAccount) : String {
        return Jwts
                .builder()
                .setSubject(userAccount.username)
                .setExpiration(generateExpiryDate())
                .claim("roles", userAccount.authorities)
                .claim("created", Date())
                .signWith(SignatureAlgorithm.HS512, securityProperties.secret)
                .compact()
    }


    private fun generateExpiryDate(): Date {
        return  Date().add(Calendar.MINUTE, securityProperties.expiration)
    }

    fun decodeClaims(token: String): Map<String, Any?>{
        val claims = Jwts.parser().setSigningKey(securityProperties.secret).parseClaimsJws(token)

        return  claims.body as Map<String, Any?>
    }
}


