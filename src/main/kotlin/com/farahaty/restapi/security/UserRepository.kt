package com.farahaty.restapi.security

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserRepository: JpaRepository<User,Long> {

    fun findOneByEmail(email: String): User?
}