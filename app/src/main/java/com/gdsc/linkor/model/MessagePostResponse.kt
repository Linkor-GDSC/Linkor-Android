package com.gdsc.linkor.model

data class MessagePostResponse(
    val `data`: Message,
    val message: String,
    val success: String
)