package com.gdsc.linkor.model

data class Message(
    val content: String,
    val senderEmail: String,
    val receiverEmail: String,
    val regDate: String,
)