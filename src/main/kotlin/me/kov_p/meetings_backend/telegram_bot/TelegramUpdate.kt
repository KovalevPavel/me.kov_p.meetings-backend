package me.kov_p.meetings_backend.telegram_bot

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
internal data class TelegramUpdate @OptIn(ExperimentalSerializationApi::class) constructor(
    @JsonNames("update_id")
    val updateId: Long?,
    @JsonNames("message")
    val messageInfo: MessageInfo?,
    @JsonNames("edited_message")
    val editedMessage: MessageInfo?,
) {

    @Serializable
    data class MessageInfo @OptIn(ExperimentalSerializationApi::class) constructor(
        @JsonNames("message_id")
        val messageId: Long?,
        @JsonNames("from")
        val authorInfo: ChatInfo?,
        @JsonNames("chat")
        val chatInfo: ChatInfo?,
        @JsonNames("date")
        val date: Long?,
        @JsonNames("left_chat_member")
        val leftChatMember: ChatInfo?,
        @JsonNames("new_chat_member")
        val newChatMember: ChatInfo?,
        @JsonNames("edit_date")
        val editDate: Long?,
        @JsonNames("sticker")
        val stickerInfo: AttachmentInfo?,
        @JsonNames("photo")
        val photoInfo: List<AttachmentInfo>?,
        @JsonNames("text")
        val text: String?,
        @JsonNames("caption")
        val caption: String?,
        @JsonNames("edit_text")
        val editText: String?,
    )

    @Serializable
    data class ChatInfo(
        @JsonNames("id")
        val chatId: Long?,
        @JsonNames("is_bot")
        val isBot: Boolean?,
        @JsonNames("first_name")
        val firstName: String?,
        @JsonNames("last_name")
        val lastName: String?,
        @JsonNames("username")
        val userName: String?,
        @JsonNames("title")
        val title: String?,
        @JsonNames("type")
        val type: String?
    )

    @Serializable
    data class AttachmentInfo(
        @JsonNames("file_id")
        val fileId: String,
        @JsonNames("file_unique_id")
        val fileUniqueId: String
    )
}
