package com.example.mobdeve_mco

import android.os.Parcel
import android.os.Parcelable

data class Listing(val id:Int,
                   val imageList:ArrayList<Int>,
                   val title:String,
                   val price:Int,
                   val property: String,
                   val propertyId: Int,
                   val university: String,

                   val area: Double,
                   val isFurnished: Boolean,
                   val isStudioType: Boolean,
                   val numBedroom: Int,
                   val numBathroom: Int,
                   val floor: Int,
                   val balcony: Boolean,
                   val ownerId: Int,
                   val description: String,

                    val isRented: Boolean,
    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.createIntArray()?.toCollection(ArrayList()) ?: ArrayList(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,

        parcel.readDouble(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
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

        parcel.writeDouble(area)
        parcel.writeByte(if (isFurnished) 1 else 0)
        parcel.writeByte(if (isStudioType) 1 else 0)
        parcel.writeInt(numBedroom)
        parcel.writeInt(numBathroom)
        parcel.writeInt(floor)
        parcel.writeByte(if (balcony) 1 else 0)
        parcel.writeInt(ownerId)
        parcel.writeString(description)
        parcel.writeByte(if (isRented) 1 else 0)
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
