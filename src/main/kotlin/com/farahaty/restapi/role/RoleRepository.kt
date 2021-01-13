package com.farahaty.restapi.role

import com.farahaty.restapi.role.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface RoleRepository: JpaRepository<Role,Long> {
    fun findByRolename(rolename: String): Role
}