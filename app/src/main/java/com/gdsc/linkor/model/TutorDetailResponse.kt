package com.gdsc.linkor.model

import com.google.gson.annotations.SerializedName

data class TutorDetailResponse(
    @SerializedName("data")
    val tutor: Tutor,
    val message: String,
    val success: String
)