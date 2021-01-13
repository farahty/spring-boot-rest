package com.farahaty.restapi.security

import com.farahaty.restapi.add
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*


class JWTUtil(
    private val securityProperties: SecurityProperties
) {


    fun generateToken(user: User) : String {


        val claims: HashMap<String, Any?> = HashMap()
        claims["sub"] = user.email
        claims["created"] = Date()
        claims["roles"] = user.roles

        return Jwts
                .builder()
                .setClaims(claims)
                .setExpiration(generateExpiryDate())
                .signWith(SignatureAlgorithm.HS512, securityProperties.secret)
                .compact()
    }


    private fun generateExpiryDate(): Date {
        return  Date().add(Calendar.MINUTE, securityProperties.expiration)
    }
}


