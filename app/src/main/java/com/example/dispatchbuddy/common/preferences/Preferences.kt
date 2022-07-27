package com.example.dispatchbuddy.common.preferences

interface Preferences {
    // save user tokens
    fun saveToken(token: String)
    fun getToken(): String
    fun deleteTokenInfo()
}