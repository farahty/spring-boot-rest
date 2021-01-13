package com.farahaty.restapi.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/auth")
class AuthController(
    val authService: AuthService,
    val authenticationManager: AuthenticationManager,
    val securityProperties: SecurityProperties

) {


    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest): RegisterResponse {

        val user = authService.register(registerRequest)
        val jwtUtil = JWTUtil(securityProperties)
        val token = jwtUtil.generateToken(user)


        return  RegisterResponse(token,user)

    }




}