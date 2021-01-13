package com.farahaty.restapi.security

import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@EnableWebSecurity
class SecurityConfig: WebSecurityConfigurerAdapter() {

    val PUBLIC_ENPOINTS = arrayOf("/api/auth/**")

    override fun configure(http: HttpSecurity?) {

        http
            // disable cors and csrf
            ?.cors()
            ?.and()
            ?.csrf()
            ?.disable()

            // set the session policy
            ?.sessionManagement()
            ?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            // configure authentication filters
            ?.and()
            ?.addFilter(JWTLoginFilter(authenticationManager(), securityProperties()))
            ?.addFilter(JWTAuthFilter(authenticationManager(), securityProperties()))

            // configure the routes permission
            ?.authorizeRequests()
            ?.antMatchers(*PUBLIC_ENPOINTS)
            ?.permitAll()
            ?.anyRequest()
            ?.authenticated()

    }

    @Bean
    fun passwordEncoderAndMatcher(): PasswordEncoder {
        return object : PasswordEncoder {
            override fun encode(rawPassword: CharSequence?): String = BCryptPasswordEncoder().encode(rawPassword)
            override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean = BCryptPasswordEncoder().matches(rawPassword, encodedPassword)
        }
    }

    @Bean
    override fun authenticationManager(): AuthenticationManager = super.authenticationManager()

    @Bean
    fun securityProperties () : SecurityProperties = SecurityProperties()
}