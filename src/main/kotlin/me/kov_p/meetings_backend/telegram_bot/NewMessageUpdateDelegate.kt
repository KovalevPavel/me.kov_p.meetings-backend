package me.kov_p.meetings_backend.telegram_bot

import me.kov_p.meetings_backend.database.dao.user.UserDaoImpl
import me.kov_p.meetings_backend.database.models.UserDto
import me.kov_p.meetings_backend.plugins.UpdateEventDelegate

class NewMessageUpdateDelegate : UpdateEventDelegate {

    override fun isDelegateValid(updateVo: UpdateVo): Boolean {
        return updateVo is UpdateVo.NewMessage
    }

    override fun handleUpdate(updateVo: UpdateVo) {
        println("update: $updateVo")
        if (updateVo !is UpdateVo.NewMessage) return
//        BotHandler.sendMessage(
//            message = "new message -> ${updateVo.text} from user ${updateVo.author.userName} with id ${updateVo.author.id}",
//            chatId = ChatId.fromId(updateVo.chatId),
//        )
        UserDaoImpl().createUser(
            UserDto(
                userName = updateVo.author.userName,
                chatId = updateVo.author.id.toInt()
            )
        )
    }
}