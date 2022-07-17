package me.kov_p.meetings_backend

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import me.kov_p.meetings_backend.database.DatabaseHandler
import me.kov_p.meetings_backend.plugins.configureBotRouting
import me.kov_p.meetings_backend.plugins.configureRouting
import me.kov_p.meetings_backend.telegram_bot.BotHandler

fun main() {
    BotHandler.initBot()
    DatabaseHandler.init()

    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
        configureRouting()
        configureBotRouting()
    }.start(wait = true)
}
