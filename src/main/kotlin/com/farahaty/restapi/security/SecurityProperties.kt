package com.farahaty.restapi.security
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "auth")
class SecurityProperties {
    var secret = ""
    var expiration: Int = 15
    var tokenPrefix = "Bearer"
    var header = "Authorization"
    var strength = 10
}