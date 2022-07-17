package me.kov_p.meetings_backend.telegram_bot.update_delegates

import com.github.kotlintelegrambot.entities.ChatId
import io.ktor.util.toLowerCasePreservingASCIIRules
import me.kov_p.meetings_backend.telegram_bot.BotHandler
import me.kov_p.meetings_backend.telegram_bot.UpdateVo

class NewMessageUpdateDelegate : UpdateEventDelegate {

    override fun isDelegateValid(updateVo: UpdateVo): Boolean {
        return updateVo is UpdateVo.NewMessage
    }

    override fun handleUpdate(updateVo: UpdateVo) {
        when (updateVo) {
            is UpdateVo.NewMessage -> {
                checkForMeetingsRequest(updateVo.text)
                    ?.let {
                        BotHandler.sendMessage(
                            message = it,
                            chatId = ChatId.fromId(updateVo.chatId),
                            replyToMessage = updateVo.id
                        )
                    }
            }
            else -> return
        }
    }

    private fun checkForMeetingsRequest(message: String): String? {
        val formattedMessage = message
            .toLowerCasePreservingASCIIRules()
            .filter { it.isLetterOrDigit() }

        val triggers = System.getenv(MEETING_TRIGGERS_KEY).splitToSequence(DELIMITER_CHARACTER)

        return when {
            triggers.any(formattedMessage::contains) -> {
                val answers =
                    System.getenv(MEETING_ANSWERS_KEY).splitToSequence(DELIMITER_CHARACTER)
                answers.toList().random()
            }
            else -> {
                null
            }
        }
    }

    companion object {
        private const val MEETING_TRIGGERS_KEY = "we_gonna_meet_triggers"
        private const val MEETING_ANSWERS_KEY = "we_gonna_meet_answers"
        private const val DELIMITER_CHARACTER = "%"
    }
}