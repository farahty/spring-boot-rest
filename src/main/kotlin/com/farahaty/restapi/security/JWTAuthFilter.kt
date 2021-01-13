package com.farahaty.restapi.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JWTAuthFilter(authenticationManager: AuthenticationManager, val securityProperties: SecurityProperties): BasicAuthenticationFilter(authenticationManager) {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        val header = request.getHeader(securityProperties.header)

        if (header == null || header.split(" ").size != 2) {
            chain.doFilter(request, response)
            return
        }
        val (prefix,token) = header.split(" ")

        if(prefix.toLowerCase() != securityProperties.tokenPrefix.toLowerCase()){
            chain.doFilter(request, response)
            return
        }

        getAuthentication(token)?.also {
            SecurityContextHolder.getContext().authentication = it
        }

        chain.doFilter(request, response)
    }


    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken? {
        return try {
            val claims = JWTUtil(securityProperties).decodeClaims(token)
            val authorities = ArrayList<GrantedAuthority>()
            (claims["roles"] as List<*>).forEach { role -> authorities.add(SimpleGrantedAuthority(role.toString())) }

            UsernamePasswordAuthenticationToken(claims["sub"], null, authorities)
        } catch (e: Exception) {
            return null
        }
    }
}