package me.kov_p.meetings_backend.telegram_bot

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.types.TelegramBotResult
import com.github.kotlintelegrambot.webhook
import me.kov_p.meetings_backend.ConfigHandler

object BotHandler {
    private const val BOT_TOKEN_CONFIG_KEY = "bot_token"

    private val allowedUpdatesList = listOf(
        "message",
        "edited_message",
    )
    private val botToken = System.getenv(BOT_TOKEN_CONFIG_KEY)

    private val bot = bot {
        token = botToken
        webhook {
            allowedUpdates = allowedUpdatesList
            url = ConfigHandler.botUpdateUrl
        }
    }

    fun initBot() {
        bot.startWebhook()
    }

    fun sendMessage(message: String, chatId: ChatId, replyToMessage: Long? = null): TelegramBotResult<Message> {
        return bot.sendMessage(
            chatId = chatId,
            text = message,
            replyToMessageId = replyToMessage,
            allowSendingWithoutReply = true
        )
    }
}