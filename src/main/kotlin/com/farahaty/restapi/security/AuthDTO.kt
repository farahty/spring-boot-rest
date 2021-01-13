package com.farahaty.restapi.security

import com.farahaty.restapi.user.User


data class LoginRequest(val email: String, val password: String)

data class LoginResponse(val user: User, val accessToken: String)

data class RegisterRequest(val email: String, val password: String)

data class RegisterResponse( val user: User, val accessToken: String)