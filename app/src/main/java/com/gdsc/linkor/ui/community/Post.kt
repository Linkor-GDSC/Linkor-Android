package com.gdsc.linkor.ui.community

import com.gdsc.linkor.ui.community.data.post.Post2

data class Post(
    val id: Int, val photoUrl: String?, val name: String,
    val title: String, val content: String, val time: String)

fun Post2.toRouteString(): String {
    return "${id}/${writerPhotoUrl}/${writer}/${title}/${content}/${createdAt}"
}

