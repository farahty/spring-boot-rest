package com.farahaty.restapi.user

import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {

    fun findAll(): List<User>{
        return userRepository.findAll()
    }
}