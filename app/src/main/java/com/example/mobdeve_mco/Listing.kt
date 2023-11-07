package com.example.mobdeve_mco

import android.os.Parcel
import android.os.Parcelable

data class Listing(
    var id: String = "",
    var imageList: ArrayList<String> = ArrayList(),
    val title: String = "",
    val price: Int = 0,
    val property: String = "",
    val propertyId: String = "",
    val university: String = "",
    val area: Double = 0.0,
    val furnished: Boolean = false,
    val studioType: Boolean = false,
    val numBedroom: Int = 0,
    val numBathroom: Int = 0,
    val floor: Int = 0,
    val balcony: Boolean = false,
    val ownerId: String = "",
    val description: String = "",
    val rented: Boolean = false
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createStringArray()?.toCollection(ArrayList()) ?: ArrayList(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,

        parcel.readDouble(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeStringArray(imageList.toTypedArray())
        parcel.writeString(title)
        parcel.writeInt(price)
        parcel.writeString(property)
        parcel.writeString(propertyId)
        parcel.writeString(university)

        parcel.writeDouble(area)
        parcel.writeByte(if (furnished) 1 else 0)
        parcel.writeByte(if (studioType) 1 else 0)
        parcel.writeInt(numBedroom)
        parcel.writeInt(numBathroom)
        parcel.writeInt(floor)
        parcel.writeByte(if (balcony) 1 else 0)
        parcel.writeString(ownerId)
        parcel.writeString(description)
        parcel.writeByte(if (rented) 1 else 0)
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
