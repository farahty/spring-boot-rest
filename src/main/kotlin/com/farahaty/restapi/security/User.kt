package com.farahaty.restapi.security

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*


@Entity
data class User(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    @JsonIgnore
    var password: String = ""
) {
    @Id
    @GeneratedValue
    var id: Long = 0
    var version: Int = 0
    var nonExpired: Boolean = true
    var nonLocked: Boolean = true
    var enabled: Boolean = true

    @OneToMany(fetch = FetchType.EAGER, cascade = arrayOf(CascadeType.ALL))
    var roles: Set<Role> = HashSet()

    constructor(user: User) : this(user.firstName, user.lastName,  user.email, user.password) {
        id = user.id
        version = user.version
        firstName = user.firstName
        lastName = user.lastName
        email = user.email
        password = user.password
        nonExpired = user.nonExpired
        nonLocked = user.nonLocked
        enabled = user.enabled
        roles = user.roles
    }
}