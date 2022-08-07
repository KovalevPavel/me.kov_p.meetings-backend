package me.kov_p.meetings_backend.login.domain.interactors

import me.kov_p.meetings_backend.tg_bot.domain.BotHandler

class SendCodeToUserInteractor(
    private val botHandler: BotHandler,
) {
    private val messageTemplate = System.getenv(SEND_CODE_MESSAGE_KEY)

    operator fun invoke(chatId: Long, confirmationCode: Int): Boolean {
        val message = messageTemplate.replace(REPLACEMENT_CHAR, confirmationCode.toString())

        return botHandler.sendMessage(chatId = chatId, message = message).isSuccess
    }

    companion object {
        private const val SEND_CODE_MESSAGE_KEY = "send_confirmation_code_template"
        private const val REPLACEMENT_CHAR = "%"
    }
}
