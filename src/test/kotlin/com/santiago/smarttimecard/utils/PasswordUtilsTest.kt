package com.santiago.smarttimecard.utils


import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class PasswordUtilsTest {

    private val PASSWORD = "123456"
    private val bCryptEncoder = BCryptPasswordEncoder()

    @Test
    fun testGenerateHashPassword() {
        val hash = PasswordUtils().generateBCript(PASSWORD)
        Assertions.assertTrue(bCryptEncoder.matches(PASSWORD, hash))
    }

    @Test
    fun testWrongGenerateHashPassword() {
        val DifferentPass = "12345"
        val hash = PasswordUtils().generateBCript(PASSWORD)
        Assertions.assertFalse(bCryptEncoder.matches(DifferentPass, hash))
    }
}