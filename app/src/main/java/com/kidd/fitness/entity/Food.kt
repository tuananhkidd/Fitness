package com.kidd.fitness.entity

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

class Food(
    val id: String = "",
    val name: String = "",
    val calo: Double= 0.0,
    val dailyTime :DailyTime = DailyTime.EVENING,
    @ServerTimestamp
    val createdDate: Date = Date()
)