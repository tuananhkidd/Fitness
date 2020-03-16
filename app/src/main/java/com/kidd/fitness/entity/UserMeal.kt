package com.kidd.fitness.entity

import android.os.Parcel
import android.os.Parcelable

class UserMeal constructor() : Parcelable{
    constructor( id: String, target_calo: Double,createdDate: String) : this() {
        this.id = id
        this.target_calo = target_calo
        this.createdDate = createdDate
    }
    var id: String = ""
    var target_calo: Double = 0.0
    var createdDate: String = ""
    var morning_calo:Double = 0.0
    var afternoon_calo:Double = 0.0
    var evening_calo:Double = 0.0

    constructor(parcel: Parcel) : this() {
        id = parcel.readString().toString()
        target_calo = parcel.readDouble()
        createdDate = parcel.readString().toString()
        morning_calo = parcel.readDouble()
        afternoon_calo = parcel.readDouble()
        evening_calo = parcel.readDouble()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeDouble(target_calo)
        parcel.writeString(createdDate)
        parcel.writeDouble(morning_calo)
        parcel.writeDouble(afternoon_calo)
        parcel.writeDouble(evening_calo)
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