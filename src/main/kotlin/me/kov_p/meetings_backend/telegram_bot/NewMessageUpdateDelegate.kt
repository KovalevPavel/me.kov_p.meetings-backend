package me.kov_p.meetings_backend.telegram_bot

import com.github.kotlintelegrambot.entities.ChatId
import me.kov_p.meetings_backend.plugins.UpdateEventDelegate

class NewMessageUpdateDelegate : UpdateEventDelegate {

    override fun isDelegateValid(updateVo: UpdateVo): Boolean {
        return updateVo is UpdateVo.NewMessage
    }

    override fun handleUpdate(updateVo: UpdateVo) {
        if (updateVo !is UpdateVo.NewMessage) return
        BotHandler.sendMessage(
            message = "new message -> ${updateVo.text} from user ${updateVo.author.userName} with id ${updateVo.author.id}",
            chatId = ChatId.fromId(updateVo.chatId),
        )
    }
}