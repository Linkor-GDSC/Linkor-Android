package com.gdsc.linkor

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

data class Tutor(
    val photoUrl:String,
    val name:String,
    val gender:String,
    val locationSido:String,
    val locationGu:String,
    val time:String,
    val tutoringMethod:String,
    val introduction:String
)

fun Tutor.toRouteString(): String {
    val photoUrl= URLEncoder.encode(photoUrl, StandardCharsets.UTF_8.toString())
    return "${photoUrl}/${name}/${gender}/${locationGu}/${locationSido}/${time}/${tutoringMethod}/${introduction}"
}
