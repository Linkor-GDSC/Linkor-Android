package com.gdsc.linkor.ui.community.data.post

data class GetPostId(
    val data: String,
    val message: String,
    val success: String
)
data class PostId(
    val content: String,
    val createdAt: String,
    val id: Int,
    val title: String,
    val writer: String
)

