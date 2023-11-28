package com.example.mobdeve_mco

import java.util.Date

data class User(
    val id: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val contactNum : String = "",
    val dateAccountCreated: Date = Date(),
    val bio: String? = "",
    val pfp: String? = "",
    val likes: ArrayList<String>? = ArrayList()
)
