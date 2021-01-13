package com.farahaty.restapi

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.util.*

fun Any.toJSON(): String = jacksonObjectMapper().writeValueAsString(this)


fun Date.add(field: Int, amount: Int): Date {
    Calendar.getInstance().apply {
        time = this@add
        add(field, amount)
        return time
    }
}