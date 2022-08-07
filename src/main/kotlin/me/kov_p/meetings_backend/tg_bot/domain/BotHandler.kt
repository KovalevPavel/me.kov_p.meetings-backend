package me.kov_p.meetings_backend.tg_bot.domain

interface BotHandler {
    fun sendMessage(message: String, chatId: Long): BotMessageResult
}
