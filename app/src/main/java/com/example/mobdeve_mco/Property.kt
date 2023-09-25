package com.example.mobdeve_mco

import android.os.Parcel
import android.os.Parcelable



data class Property(val imageList:ArrayList<Int>, val name:String, val highestPrice:Int, val lowestPrice:Int, val numListings:Int, val university: String, val amenities: Map<Amenity, Boolean>) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createIntArray()?.toCollection(ArrayList()) ?: ArrayList(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        readAmenitiesFromParcel(parcel)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeIntArray(imageList.toIntArray())
        parcel.writeString(name)
        parcel.writeInt(highestPrice)
        parcel.writeInt(lowestPrice)
        parcel.writeInt(numListings)
        parcel.writeString(university)
        writeAmenitiesToParcel(amenities, parcel)
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

        private fun readAmenitiesFromParcel(parcel: Parcel): Map<Amenity, Boolean> {
            val amenityMap = mutableMapOf<Amenity, Boolean>()
            val amenityNames = Amenity.values().map { it.name }

            for (amenityName in amenityNames) {
                amenityMap[Amenity.valueOf(amenityName)] = parcel.readByte() != 0.toByte()
            }

            return amenityMap
        }

        private fun writeAmenitiesToParcel(amenities: Map<Amenity, Boolean>, parcel: Parcel) {
            val amenityNames = Amenity.values().map { it.name }

            for (amenityName in amenityNames) {
                parcel.writeByte(if (amenities[Amenity.valueOf(amenityName)] == true) 1 else 0)
            }
        }
    }
}