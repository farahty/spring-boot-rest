package com.farahaty.restapi.security

import com.farahaty.restapi.user.User
import com.farahaty.restapi.user.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthService(
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val securityProperties: SecurityProperties
) : UserDetailsService {


    /*
    * be used by spring security to find the user by email
    */
    override fun loadUserByUsername(email: String): UserDetails {
        val user =
            userRepository.findOneByEmail(email) ?: throw NoSuchElementException("user with email $email not found");
        return UserAccount(user)
    }


    fun register(registerRequest: RegisterRequest): RegisterResponse {
        val password = passwordEncoder.encode(registerRequest.password)
        val user = userRepository.save(User(email = registerRequest.email, password = password))
        val userAccount = UserAccount(user)
        val token = JWTUtil(securityProperties).generateToken(userAccount)

        return RegisterResponse(user, token )

    }


}