package com.example.mobdeve_mco

import android.os.Parcel
import android.os.Parcelable

data class Listing(val id:Int, val imageList:ArrayList<Int>, val title:String, val price:Int, val property: String, val propertyId: Int, val university: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.createIntArray()?.toCollection(ArrayList()) ?: ArrayList(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeIntArray(imageList.toIntArray())
        parcel.writeString(title)
        parcel.writeInt(price)
        parcel.writeString(property)
        parcel.writeInt(propertyId)
        parcel.writeString(university)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Listing> {
        override fun createFromParcel(parcel: Parcel): Listing {
            return Listing(parcel)
        }

        override fun newArray(size: Int): Array<Listing?> {
            return arrayOfNulls(size)
        }
    }
}
