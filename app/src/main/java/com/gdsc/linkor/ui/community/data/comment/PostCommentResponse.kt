package com.gdsc.linkor.ui.community.data.comment

data class PostCommentResponse(
    val `data`: Comment,
    val message: String,
    val success: String
)


data class Comment(
    val content: String,
    val id: Int,
    val writer: String
)