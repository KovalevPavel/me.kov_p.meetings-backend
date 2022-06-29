package me.kov_p.meetings_backend.telegram_bot

import com.github.kotlintelegrambot.entities.ChatId
import me.kov_p.meetings_backend.plugins.UpdateEventDelegate

class EditMessageDelegate : UpdateEventDelegate {

    override fun isDelegateValid(updateVo: UpdateVo): Boolean {
        return updateVo is UpdateVo.EditedMessage
    }

    override fun handleUpdate(updateVo: UpdateVo) {
        if (updateVo !is UpdateVo.EditedMessage) return
        BotHandler.sendMessage(
            message = System.getenv(EDIT_ALERT_CONFIG_KEY),
            chatId = ChatId.fromId(updateVo.originalMessage.chatId),
            replyToMessage = updateVo.originalMessage.id
        )
    }

    companion object {
        private const val EDIT_ALERT_CONFIG_KEY = "edit_alert"
    }
}
