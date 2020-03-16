package com.kidd.fitness.extension

fun Double?.format(digits: Int = 1)= "%.${digits}f".format(this)