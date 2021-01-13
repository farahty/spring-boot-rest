package com.farahaty.restapi.user

import com.farahaty.restapi.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserRepository: JpaRepository<User,Long> {

    fun findOneByEmail(email: String): User?
}