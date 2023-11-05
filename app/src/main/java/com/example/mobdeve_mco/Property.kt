package com.example.mobdeve_mco

import android.os.Parcel
import android.os.Parcelable



data class Property(
    val id: String = "",
    val imageList: ArrayList<String> = ArrayList(),
    val description: String = "",
    val name: String = "",
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
    val address: String = "",
    val highestPrice: Int = 0,
    val lowestPrice: Int = 0,
    val numListings: Int = 0,
    val university: String = "",
    val swimmingPool: Boolean = false,
    val gym: Boolean = false,
    val parking: Boolean = false,
    val wifi: Boolean = false,
    val elevators: Boolean = false,
    val fireAlarm: Boolean = false,
    val security: Boolean = false,
    val generator: Boolean = false,
    val cctv: Boolean = false,
    val waterTank: Boolean = false,
    val mailRoom: Boolean = false,
    val listingIds: ArrayList<String> = ArrayList()
) : Parcelable {


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
        parcel.writeByte(if (swimmingPool) 1 else 0)
        parcel.writeByte(if (gym) 1 else 0)
        parcel.writeByte(if (parking) 1 else 0)
        parcel.writeByte(if (wifi) 1 else 0)
        parcel.writeByte(if (elevators) 1 else 0)
        parcel.writeByte(if (fireAlarm) 1 else 0)
        parcel.writeByte(if (security) 1 else 0)
        parcel.writeByte(if (generator) 1 else 0)
        parcel.writeByte(if (cctv) 1 else 0)
        parcel.writeByte(if (waterTank) 1 else 0)
        parcel.writeByte(if (mailRoom) 1 else 0)
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