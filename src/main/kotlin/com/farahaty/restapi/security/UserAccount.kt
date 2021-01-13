package com.farahaty.restapi.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

data class UserAccount(var user:User): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return user
            .roles
            .stream()
            .map { role ->

                SimpleGrantedAuthority(role.toString())
            }
            .collect(Collectors.toList())
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.email
    }

    override fun isAccountNonExpired(): Boolean {
        return user.nonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return user.nonLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return user.nonExpired
    }

    override fun isEnabled(): Boolean {
        return user.enabled
    }
}