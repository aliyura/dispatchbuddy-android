package com.example.dispatchbuddy.common.preferences

interface Preferences {
    // save user tokens
    fun saveToken(token: String)
    fun getToken(): String
    fun deleteTokenInfo()
    //save user ID
    fun saveUserId(id: String)
    fun getUserId(): String
    fun deleteUserId()
    // save user riderDetails
    fun savePickUp(pickup: String)
    fun saveDestination(destination: String)
    // get user details
    fun getPickUp(): String
    fun getDestination(): String
    //save user email and get user email
    fun saveEmail(email: String)
    fun getEmail(): String
}