package me.kov_p.meetings_backend.telegram_bot

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
internal data class TelegramUpdate(
    @SerializedName("update_id")
    val updateId: Long?,
    @SerializedName("message")
    val messageInfo: MessageInfo?,
    @SerializedName("edited_message")
    val editedMessage: MessageInfo?,
) {

    @Serializable
    data class MessageInfo(
        @SerializedName("message_id")
        val messageId: Long?,
        @SerializedName("from")
        val authorInfo: ChatInfo?,
        @SerializedName("chat")
        val chatInfo: ChatInfo?,
        @SerializedName("date")
        val date: Long?,
        @SerializedName("left_chat_member")
        val leftChatMember: ChatInfo?,
        @SerializedName("new_chat_member")
        val newChatMember: ChatInfo?,
        @SerializedName("edit_date")
        val editDate: Long?,
        @SerializedName("sticker")
        val stickerInfo: AttachmentInfo?,
        @SerializedName("photo")
        val photoInfo: List<AttachmentInfo>?,
        @SerializedName("text")
        val text: String?,
        @SerializedName("caption")
        val caption: String?,
        @SerializedName("edit_text")
        val editText: String?,
    )

    @Serializable
    data class ChatInfo(
        @SerializedName("id")
        val chatId: Long?,
        @SerializedName("is_bot")
        val isBot: Boolean?,
        @SerializedName("first_name")
        val firstName: String?,
        @SerializedName("last_name")
        val lastName: String?,
        @SerializedName("username")
        val userName: String?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("type")
        val type: String?
    )

    @Serializable
    data class AttachmentInfo(
        @SerializedName("file_id")
        val fileId: String,
        @SerializedName("file_unique_id")
        val fileUniqueId: String
    )
}
