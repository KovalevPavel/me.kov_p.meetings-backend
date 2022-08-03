package me.kov_p.meetings_backend.login

import com.github.kotlintelegrambot.entities.ChatId
import me.kov_p.meetings_backend.telegram_bot.BotHandler

class SendCodeToUserInteractor {
    private val messageTemplate = System.getenv(SEND_CODE_MESSAGE_KEY)

    operator fun invoke(userLogin: String, confirmationCode: Int): Boolean {
        val message = messageTemplate.replace(REPLACEMENT_CHAR, confirmationCode.toString())

        return BotHandler.sendMessage(chatId = ChatId.fromChannelUsername(userLogin), message = message).isSuccess
    }

    companion object {
        private const val SEND_CODE_MESSAGE_KEY = "send_confirmation_code_template"
        private const val REPLACEMENT_CHAR = "%"
    }
}