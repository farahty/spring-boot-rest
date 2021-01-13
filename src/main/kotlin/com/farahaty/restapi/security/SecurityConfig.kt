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
            ?.cors()
            ?.and()
            ?.csrf()
            ?.disable()


            ?.sessionManagement()
            ?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            ?.and()
            ?.addFilter(JWTLoginFilter(authenticationManager(), securityProperties()))
            ?.addFilter(JWTAuthFilter(authenticationManager()))
            ?.authorizeRequests()
            ?.antMatchers(*PUBLIC_ENPOINTS)
            ?.permitAll()
            ?.anyRequest()
            ?.authenticated()

    }



    @Bean
    fun passwordEncoderAndMatcher(): PasswordEncoder {
        return object : PasswordEncoder {
            override fun encode(rawPassword: CharSequence?): String {
                return BCryptPasswordEncoder().encode(rawPassword)
            }
            override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
                return BCryptPasswordEncoder().matches(rawPassword, encodedPassword)
            }
        }
    }

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    @Bean
    fun securityProperties () : SecurityProperties {
        return SecurityProperties()
    }
}