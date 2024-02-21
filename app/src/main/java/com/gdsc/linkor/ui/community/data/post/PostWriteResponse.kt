package com.gdsc.linkor.ui.community.data.post

data class PostWriteResponse(
    val success: String?,
    val message: String?,
    val data: List<Post2>?,
)

data class Post2(
    val id: Int?,
    val title: String?,
    val content: String?,
    val writer: String?,
    val writerPhotoUrl: String?,
    val createdAt: String?,

)
