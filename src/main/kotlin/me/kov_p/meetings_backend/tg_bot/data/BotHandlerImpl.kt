package me.kov_p.meetings_backend.tg_bot.data

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.logging.LogLevel
import com.github.kotlintelegrambot.types.TelegramBotResult
import me.kov_p.meetings_backend.database.dao.TgBotDao
import me.kov_p.meetings_backend.tg_bot.domain.BotHandler
import me.kov_p.meetings_backend.tg_bot.domain.BotMessageResult

class BotHandlerImpl(
    private val botDao: TgBotDao,
) : BotHandler {
    private val bot by lazy {
        bot {
            token = botDao.getBotToken()
            logLevel = LogLevel.All()
        }
    }

    override fun sendMessage(message: String, chatId: Long): BotMessageResult {
        val result = bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = message,
            parseMode = ParseMode.MARKDOWN_V2
        )

        return when (result) {
            is TelegramBotResult.Success -> BotMessageResult()
            is TelegramBotResult.Error -> handleBotError(result = result)
        }
    }

    private fun handleBotError(result: TelegramBotResult.Error<Message>): BotMessageResult {
        val errorMessage = when (result) {
            is TelegramBotResult.Error.HttpError -> result.description
            is TelegramBotResult.Error.TelegramApi -> result.description
            is TelegramBotResult.Error.InvalidResponse -> result.body.toString()
            is TelegramBotResult.Error.Unknown -> result.exception.toString()
        }
        return BotMessageResult(isSuccess = false, errorMessage = errorMessage.orEmpty())
    }
}
