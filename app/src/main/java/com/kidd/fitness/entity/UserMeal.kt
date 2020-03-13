package com.kidd.fitness.entity

import android.os.Parcel
import android.os.Parcelable

class UserMeal constructor() : Parcelable{
    constructor( id: String, target_calo: Int,createdDate: String) : this() {
        this.id = id
        this.target_calo = target_calo
        this.createdDate = createdDate
    }
    var id: String = ""
    var target_calo: Int = 0
    var createdDate: String = ""
    var morning_calo:Int = 0
    var afternoon_calo:Int = 0
    var evening_calo:Int = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readString().toString()
        target_calo = parcel.readInt()
        createdDate = parcel.readString().toString()
        morning_calo = parcel.readInt()
        afternoon_calo = parcel.readInt()
        evening_calo = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(target_calo)
        parcel.writeString(createdDate)
        parcel.writeInt(morning_calo)
        parcel.writeInt(afternoon_calo)
        parcel.writeInt(evening_calo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserMeal> {
        override fun createFromParcel(parcel: Parcel): UserMeal {
            return UserMeal(parcel)
        }

        override fun newArray(size: Int): Array<UserMeal?> {
            return arrayOfNulls(size)
        }
    }
}