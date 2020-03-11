package com.kidd.fitness.entity

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

class Food(
    val id: String,
    val name: String,
    val calo: String,
    val dailyTime: DailyTime,
    @ServerTimestamp
    val createdDate: Date = Date()
)