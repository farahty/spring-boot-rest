package com.farahaty.restapi.security

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/auth")
class AuthController(val authService: AuthService) {


    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest): RegisterResponse{
        return  authService.register(registerRequest)
    }

}