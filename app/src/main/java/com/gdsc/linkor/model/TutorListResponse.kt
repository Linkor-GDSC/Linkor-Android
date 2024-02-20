package com.gdsc.linkor.model

import com.google.gson.annotations.SerializedName

data class TutorListResponse(
    @SerializedName("data")
    val tutor: List<Tutor>,
    val message: String,
    val success: String
)