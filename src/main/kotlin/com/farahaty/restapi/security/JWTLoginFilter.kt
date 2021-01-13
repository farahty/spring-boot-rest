package com.farahaty.restapi.security

import com.farahaty.restapi.toJSON
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTLoginFilter (
    private val authManager: AuthenticationManager,
    private val securityProperties: SecurityProperties
) : UsernamePasswordAuthenticationFilter() {

    init {
        setFilterProcessesUrl("/api/auth/login")
    }


    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        return try {
            val mapper = jacksonObjectMapper()

            val loginRequest = mapper
                .readValue<LoginRequest>(request!!.inputStream)

            authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    loginRequest.email,
                    loginRequest.password,
                )
            )
        } catch (e: IOException) {
            throw AuthenticationServiceException(e.message)
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {

        val userDetails = authResult?.principal

        if(userDetails !is UserAccount){
            throw AuthenticationServiceException("User Details is not correct")
        }

        val token = JWTUtil(securityProperties).generateToken(userDetails)

        response?.setHeader(securityProperties.header, securityProperties.tokenPrefix + " " +   token )
        val body = LoginResponse(userDetails.user, token).toJSON()

        response?.setHeader("content-type", "application/json")
        response?.writer?.write(body)
        response?.writer?.flush()
    }
}

