package com.example.mobdeve_mco

fun Int.formatPrice(): String {
    val formatter = java.text.DecimalFormat("#,###")
    return "₱" + formatter.format(this.toLong())
}