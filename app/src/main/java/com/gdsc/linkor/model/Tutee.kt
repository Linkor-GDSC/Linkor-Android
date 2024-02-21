package com.gdsc.linkor.model

import com.google.gson.annotations.SerializedName

data class Tutee(
    val email: String,
    val gender: String,
    val introduction: String,
    val locationgu: String,
    val locationsido: String,
    val name: String,
    @SerializedName("photourl")
    val photoUrl: String,
    val role: String,
    val tutoringmethod: String
)