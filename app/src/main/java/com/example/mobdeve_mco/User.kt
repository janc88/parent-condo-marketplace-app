package com.example.mobdeve_mco

import java.util.Date

data class User(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val email: String,
    val dateAccountCreated: Date,
    val bio: String?,
    val pfp: String?
)
