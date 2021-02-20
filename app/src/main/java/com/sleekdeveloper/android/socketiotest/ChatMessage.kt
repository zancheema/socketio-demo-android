package com.sleekdeveloper.android.socketiotest

data class ChatMessage(
    val from: String,
    val content: String,
    val chatRoom: String
)
