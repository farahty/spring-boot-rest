package com.farahaty.restapi.security

import org.springframework.context.ApplicationContext
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthService(
    val userRepository: UserRepository,
    val applicationContext: ApplicationContext
) : UserDetailsService {


    /*
     * be used by spring security to find the user by email
     */
    override fun loadUserByUsername(email: String): UserDetails {
        val user =
            userRepository.findOneByEmail(email) ?: throw NoSuchElementException("user with email $email not found");
        return UserAccount(user)
    }


    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }


    fun register(registerRequest: RegisterRequest): User {
        val passwordEncoder: PasswordEncoder = applicationContext.getBean(PasswordEncoder::class.java)
        val password = passwordEncoder.encode(registerRequest.password)
        val user = User(email = registerRequest.email, password = password)

        return userRepository.save(user)
    }


}