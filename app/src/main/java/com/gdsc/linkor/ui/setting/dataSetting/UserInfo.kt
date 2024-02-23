package com.gdsc.linkor.ui.setting.dataSetting

data class UserInfo(
    val email: String,
    val gender: String="",
    val introduction: String="",
    val locationgu: String?="",
    val locationsido: String?="",
    val name: String?="",
    val photourl: String="",
    val role: String="",
    val tutoringmethod: String=""
)