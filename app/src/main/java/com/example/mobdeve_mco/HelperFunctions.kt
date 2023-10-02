package com.example.mobdeve_mco

import DummyData
import java.util.Calendar
import java.util.Date

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

fun getUser(userId: Int): User {
    var owner: User? = null
    for(user in DummyData.users){
        if(user.id == userId){
            owner = user
            break
        }
    }
    return owner!!
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
