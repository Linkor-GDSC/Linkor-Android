package com.gdsc.linkor.ui.community.data.comment

data class GetCommentResponse(
    val `data`: List<AllComment>,
    val message: String,
    val success: String
)

data class AllComment(
    val content: String,
    val id: Int,
    val writer: String,
    val writerPhotoUrl:String
)