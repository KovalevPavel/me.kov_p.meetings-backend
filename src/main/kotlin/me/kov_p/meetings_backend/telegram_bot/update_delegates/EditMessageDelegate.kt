package me.kov_p.meetings_backend.telegram_bot.update_delegates

import com.github.kotlintelegrambot.entities.ChatId
import me.kov_p.meetings_backend.telegram_bot.BotHandler
import me.kov_p.meetings_backend.telegram_bot.UpdateVo

class EditMessageDelegate : UpdateEventDelegate {

    override fun isDelegateValid(updateVo: UpdateVo): Boolean {
        return updateVo is UpdateVo.EditedMessage
    }

    override fun handleUpdate(updateVo: UpdateVo) {
        when (updateVo) {
            is UpdateVo.EditedMessage -> {
                BotHandler.sendMessage(
                    message = System.getenv(EDIT_ALERT_CONFIG_KEY),
                    chatId = ChatId.fromId(updateVo.originalMessage.chatId),
                    replyToMessage = updateVo.originalMessage.id
                )
            }
            else -> {
                return
            }
        }
    }

    companion object {
        private const val EDIT_ALERT_CONFIG_KEY = "edit_alert"
    }
}
