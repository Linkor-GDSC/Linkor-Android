package com.gdsc.linkor.ui.community

import android.net.Uri
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

data class Post(
    val id: Int, val photoUrl: String?, val name: String,
    val title: String, val content: String, val time: String)

fun Post.toRouteString(): String {
    val photoUrl= URLEncoder.encode(photoUrl, StandardCharsets.UTF_8.toString())
    return "${id}/${photoUrl}/${name}/${title}/${content}/${time}"
}