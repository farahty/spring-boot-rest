package com.farahaty.restapi.user

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/users")
class UserController(
    val userService: UserService
) {

    @GetMapping
    fun getAllUser(): List<User> {
        return  userService.findAll()
    }

}