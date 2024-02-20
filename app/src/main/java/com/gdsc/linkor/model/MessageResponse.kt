package com.gdsc.linkor.model

data class MessageResponse(
    val data: List<Message>?,
    val message: String,
    val success: String
)