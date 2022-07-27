package com.example.dispatchbuddy.common.validation

import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test

class FieldValidationsTest : TestCase() {

    fun testVerifyName() {
        Assert.assertTrue(FieldValidations.verifyName("Joe Biden"))
    }

    fun testVerifyEmail() {
        Assert.assertTrue(FieldValidations.verifyEmail("email1234@gmail.com"))
    }

    fun testVerifyPassword() {
        Assert.assertTrue(FieldValidations.verifyPassword("Password@123"))
    }

    @Test
    fun testVerifyPhoneNumber() {
        Assert.assertTrue(FieldValidations.verifyPhoneNumber("07019119948"))
    }

    @Test
    fun testVerifyDateOfBirth() {
        Assert.assertTrue(FieldValidations.verifyDateOfBirth("20/07/2022"))
    }
}