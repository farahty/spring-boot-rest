package com.farahaty.restapi.security

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/users")
class UserController(
    val authService: AuthService
) {



    @GetMapping
    fun getAllUser(): List<User> {

        return authService.getAllUsers()
    }



}