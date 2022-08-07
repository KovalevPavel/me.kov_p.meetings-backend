package me.kov_p.meetings_backend.tg_bot.domain

data class BotMessageResult(
    val isSuccess: Boolean = true,
    val errorMessage: String = "",
)
