package com.example.mobdeve_mco

import java.time.LocalDate
import java.util.Date

data class User(
    val id: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val dateAccountCreated: Date = Date(),
    val bio: String? = "",
    val pfp: String? = ""
)
