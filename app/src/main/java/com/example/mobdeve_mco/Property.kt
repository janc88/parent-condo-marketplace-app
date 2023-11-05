package com.example.mobdeve_mco

import android.os.Parcel
import android.os.Parcelable



data class Property(val id:String,
                    val imageList:ArrayList<String>,
                    val description: String,
                    val name:String,
                    val longitude: Double,
                    val latitude: Double,
                    val address: String,
                    val highestPrice:Int,
                    val lowestPrice:Int,
                    val numListings:Int,
                    val university: String,
                    val SWIMMING_POOL: Boolean,
                    val GYM: Boolean,
                    val PARKING: Boolean,
                    val WIFI: Boolean,
                    val ELEVATORS: Boolean,
                    val FIRE_ALARM: Boolean,
                    val SECURITY: Boolean,
                    val GENERATOR: Boolean,
                    val CCTV: Boolean,
                    val WATER_TANK: Boolean,
                    val MAILROOM: Boolean,
                    val listingIds:ArrayList<String>) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createStringArray()?.toCollection(ArrayList()) ?: ArrayList(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.createStringArray()?.toCollection(ArrayList()) ?: ArrayList(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeStringArray(imageList.toTypedArray())
        parcel.writeString(description)
        parcel.writeString(name)
        parcel.writeDouble(longitude)
        parcel.writeDouble(latitude)
        parcel.writeString(address)
        parcel.writeInt(highestPrice)
        parcel.writeInt(lowestPrice)
        parcel.writeInt(numListings)
        parcel.writeString(university)
        parcel.writeByte(if (SWIMMING_POOL) 1 else 0)
        parcel.writeByte(if (GYM) 1 else 0)
        parcel.writeByte(if (PARKING) 1 else 0)
        parcel.writeByte(if (WIFI) 1 else 0)
        parcel.writeByte(if (ELEVATORS) 1 else 0)
        parcel.writeByte(if (FIRE_ALARM) 1 else 0)
        parcel.writeByte(if (SECURITY) 1 else 0)
        parcel.writeByte(if (GENERATOR) 1 else 0)
        parcel.writeByte(if (CCTV) 1 else 0)
        parcel.writeByte(if (WATER_TANK) 1 else 0)
        parcel.writeByte(if (MAILROOM) 1 else 0)
        parcel.writeStringArray(listingIds.toTypedArray())
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