package com.gdsc.linkor.model

import com.google.gson.annotations.SerializedName
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


data class Tutor(
    val email:String = "",
    @SerializedName("photourl")
    val photoUrl:String = "",
    val name:String = "",
    val gender:String = "",
    @SerializedName("locationsido")
    val locationSido:String = "",
    @SerializedName("locationgu")
    val locationGu:String = "",
    @SerializedName("times")
    val time:List<String> = emptyList(),
    @SerializedName("tutoringmethod")
    val tutoringMethod:String = "",
    val introduction:String = ""
)

fun Tutor.toRouteString(): String {
    val photoUrl= URLEncoder.encode(photoUrl, StandardCharsets.UTF_8.toString())

    return "${photoUrl}/${name}/${gender}/${locationGu}/${locationSido}/${time}/${tutoringMethod}/${introduction}"
}
