package me.kov_p.meetings_backend.telegram_bot

import com.google.gson.Gson
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receiveText

internal suspend fun ApplicationCall.parseUpdateEntity(): TelegramUpdate {
    val respond = this.receiveText()
    return try {
        Gson().fromJson(
            respond,
            TelegramUpdate::class.java
        )
    } catch (e: Exception) {
        println("Can't convert respond $respond")
        throw e
    }
}
