package me.kov_p.meetings_backend.telegram_bot

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.types.TelegramBotResult

object BotHandler {
    private const val BOT_TOKEN_CONFIG_KEY = "bot_token"

    private val botToken = System.getenv(BOT_TOKEN_CONFIG_KEY)

    private val bot = bot {
        token = botToken
    }

    fun sendMessage(message: String, chatId: ChatId, replyToMessage: Long? = null): TelegramBotResult<Message> {
        return bot.sendMessage(
            chatId = chatId,
            text = message,
            replyToMessageId = replyToMessage,
            allowSendingWithoutReply = true,
            parseMode = ParseMode.MARKDOWN_V2
        )
    }
}