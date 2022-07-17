package me.kov_p.meetings_backend.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import me.kov_p.meetings_backend.ConfigHandler
import me.kov_p.meetings_backend.telegram_bot.EditMessageDelegate
import me.kov_p.meetings_backend.telegram_bot.NewMessageUpdateDelegate
import me.kov_p.meetings_backend.telegram_bot.TelegramUpdate
import me.kov_p.meetings_backend.telegram_bot.UpdateVo
import me.kov_p.meetings_backend.telegram_bot.parseUpdateEntity
import me.kov_p.meetings_backend.utils.orFalse
import me.kov_p.meetings_backend.utils.orZero

fun Application.configureBotRouting() {
    routing {
        post(ConfigHandler.botUpdateSubPath) {
            call.respond(status = HttpStatusCode.Accepted, message = "Success")

            val delegates by lazy {
                listOf(
                    NewMessageUpdateDelegate(),
                    EditMessageDelegate()
                )
            }

            fun mapAuthor(authorDto: TelegramUpdate.ChatInfo?) = UpdateVo.NewChatMember(
                id = authorDto?.chatId.orZero(),
                firstName = authorDto?.firstName.orEmpty(),
                secondName = authorDto?.lastName.orEmpty(),
                userName = authorDto?.userName.orEmpty(),
                isBot = authorDto?.isBot.orFalse()
            )

            fun mapMessage(messageInfo: TelegramUpdate.MessageInfo) = UpdateVo.NewMessage(
                id = messageInfo.messageId.orZero(),
                chatId = messageInfo.chatInfo?.chatId.orZero(),
                text = messageInfo.text.orEmpty(),
                author = messageInfo.authorInfo.let(::mapAuthor),
                date = messageInfo.date.orZero(),
            )

            fun mapChatMessageUpdate(messageInfo: TelegramUpdate.MessageInfo): UpdateVo = when {
                messageInfo.newChatMember != null -> mapAuthor(authorDto = messageInfo.newChatMember)
                messageInfo.leftChatMember != null -> UpdateVo.DeletedChatMember(
                    id = messageInfo.leftChatMember.chatId.orZero(),
                    isBot = messageInfo.leftChatMember.isBot.orFalse(),
                    userName = messageInfo.leftChatMember.userName.orEmpty()
                )
                else -> mapMessage(messageInfo = messageInfo)
            }

            val response = call.parseUpdateEntity()

            when {
                response.messageInfo != null -> response.messageInfo.let(::mapChatMessageUpdate)
                response.editedMessage != null -> UpdateVo.EditedMessage(
                    originalMessage = response.editedMessage.let(::mapMessage),
                    editedText = response.editedMessage.editText.orEmpty(),
                    editDate = response.editedMessage.editDate.orZero(),
                )
                else -> return@post
            }
                .let { update ->
                    delegates.firstOrNull { it.isDelegateValid(update) }
                        ?.handleUpdate(update)
                }
        }
    }
}
