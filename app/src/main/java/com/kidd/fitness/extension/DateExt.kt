package com.kidd.fitness.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date.format(): String {
    val formate = SimpleDateFormat("dd/MM/YYYY")
    return formate.format(this)
}