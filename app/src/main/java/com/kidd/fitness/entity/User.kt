package com.kidd.fitness.entity

import com.google.gson.annotations.SerializedName

class User (
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null,
    var dateOfBirth: String? = null,
    var email: String? = null,
    var avatarUrl: String? = null
)