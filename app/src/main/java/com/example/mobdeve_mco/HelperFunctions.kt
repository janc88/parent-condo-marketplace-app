package com.example.mobdeve_mco

import DummyData
import android.graphics.Paint
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Int.formatPrice(): String {
    val formatter = java.text.DecimalFormat("#,###")
    return "₱" + formatter.format(this.toLong())
}

fun createDate(year: Int, month: Int, day: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month - 1)
    calendar.set(Calendar.DAY_OF_MONTH, day)
    return calendar.time
}


fun TextView.underlineText() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun getUser(userId: String): User {
    var owner: User? = null
    for(user in DummyData.users){
        if(user.id == userId){
            owner = user
            break
        }
    }
    return owner!!
}



fun mapNumberToUniversity(number: Int): String {
    return when (number) {
        1 -> "DLSU"
        2 -> "ADMU"
        3 -> "UST"
        4 -> "UP"
        else -> ""
    }
}

fun mapUniversityToNumber(university: String): Int {
    return when (university) {
        "DLSU" -> 1
        "ADMU" -> 2
        "UST" -> 3
        "UP" -> 4
        else -> -1
    }
}

fun getProperty(propertyId: Int): Property {
    var property: Property? = null
    for(p in DummyData.propertyList){
        if(p.id == propertyId){
            property = p
            break
        }
    }
    return property!!
}

fun formatDateJoined(date: Date): String {
    val sdf = SimpleDateFormat("MMMM yyyy", Locale.US)
    return sdf.format(date)
}


fun formatFloor(floor: Int): String {
    return when {
        floor % 10 == 1 && floor % 100 != 11 -> "${floor}st"
        floor % 10 == 2 && floor % 100 != 12 -> "${floor}nd"
        floor % 10 == 3 && floor % 100 != 13 -> "${floor}rd"
        else -> "${floor}th"
    }
}

fun getRandomListings(maxNum: Int, listingIds:ArrayList<Int>): ArrayList<Listing> {
    val result = ArrayList<Listing>()
    val shuffledListings = DummyData.listingList.shuffled()
    val filteredListings = shuffledListings.filter { listingIds.contains(it.id)}
    val maxCount = minOf(maxNum, filteredListings.size)
    for (i in 0 until maxCount) {
        result.add(filteredListings[i])
    }
    return result
}

fun String.capitalizeAndReplaceUnderscore(): String {
    val words = this.split("_")
    val result = StringBuilder()

    for (word in words) {
        if (word.isNotEmpty()) {
            val capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase()
            result.append(capitalizedWord).append(" ")
        }
    }

    return result.trim().toString()
}

fun getSimilarListings(maxNum: Int, listingIds: ArrayList<Int>, listingId: Int): ArrayList<Listing> {
    val result = ArrayList<Listing>()

    val filteredListingIds = listingIds.filter { it != listingId }

    if (filteredListingIds.isEmpty()) {
        return result
    }

    val shuffledListings = DummyData.listingList.shuffled()
    val filteredListings = shuffledListings.filter { filteredListingIds.contains(it.id) }
    val maxCount = minOf(maxNum, filteredListings.size)

    for (i in 0 until maxCount) {
        result.add(filteredListings[i])
    }

    return result
}
