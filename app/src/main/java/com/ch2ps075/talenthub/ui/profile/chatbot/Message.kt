package com.ch2ps075.talenthub.ui.profile.chatbot

class Message(
    val message: String,
    val sentBy: String,
) {
    companion object {
        const val SENT_BY_USER = "user"
        const val SENT_BY_BOT = "bot"
    }
}