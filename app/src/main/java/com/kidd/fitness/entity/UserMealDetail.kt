package com.kidd.fitness.entity

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

class UserMealDetail {
    constructor()

    var foodId: String = ""
    var foodName: String = ""
    var foodWeight: Int = 0
    @ServerTimestamp
    var createDate = Date()
}