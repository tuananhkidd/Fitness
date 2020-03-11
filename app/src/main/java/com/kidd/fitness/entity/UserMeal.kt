package com.kidd.fitness.entity

import com.google.firebase.firestore.ServerTimestamp

class UserMeal constructor(){
    constructor( id: String, target_calo: Int,createdDate: String) : this() {
        this.id = id
        this.target_calo = target_calo
        this.createdDate = createdDate
    }
    var id: String = ""
    var target_calo: Int = 0
    var createdDate: String = ""
}