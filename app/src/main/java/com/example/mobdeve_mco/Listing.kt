package com.example.mobdeve_mco

import android.os.Parcel
import android.os.Parcelable
import com.denzcoskun.imageslider.models.SlideModel

data class Listing(val coverImg:Int, val imageList:ArrayList<Int>, val title:String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.createIntArray()?.toCollection(ArrayList()) ?: ArrayList(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(coverImg)
        parcel.writeIntArray(imageList.toIntArray())
        parcel.writeString(title)
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
