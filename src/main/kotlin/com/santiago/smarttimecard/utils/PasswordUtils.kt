package com.santiago.smarttimecard.utils

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class PasswordUtils {

    fun generateBCript(pass: String): String = BCryptPasswordEncoder().encode(pass)
}