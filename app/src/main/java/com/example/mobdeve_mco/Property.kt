package com.example.mobdeve_mco

import android.os.Parcel
import android.os.Parcelable

data class Property(val imageList:ArrayList<Int>, val name:String, val highestPrice:Int, val lowestPrice:Int, val numListings:Int, val university: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createIntArray()?.toCollection(ArrayList()) ?: ArrayList(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeIntArray(imageList.toIntArray())
        parcel.writeString(name)
        parcel.writeInt(highestPrice)
        parcel.writeInt(lowestPrice)
        parcel.writeInt(numListings)
        parcel.writeString(university)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Property> {
        override fun createFromParcel(parcel: Parcel): Property {
            return Property(parcel)
        }

        override fun newArray(size: Int): Array<Property?> {
            return arrayOfNulls(size)
        }
    }
}