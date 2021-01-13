package com.farahaty.restapi.security



data class LoginRequest(val email: String, val password: String)

data class LoginResponse(val user: User, val accessToken: String)

data class RegisterRequest(val email: String, val password: String)

data class RegisterResponse(val accessToken: String, val user: User)