package me.kov_p.meetings_backend

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import me.kov_p.meetings_backend.config_handler.ConfigHandler
import me.kov_p.meetings_backend.config_handler.configHandlerModule
import me.kov_p.meetings_backend.database.DatabaseHandler
import me.kov_p.meetings_backend.database.di.daoModule
import me.kov_p.meetings_backend.login.data.loginRouting
import me.kov_p.meetings_backend.login.di.loginModule
import me.kov_p.meetings_backend.plugins.configureSerialization
import me.kov_p.meetings_backend.tg_bot.di.tgBotModule
import me.kov_p.meetings_backend.utils.inject
import org.koin.core.context.startKoin
import org.koin.logger.slf4jLogger

fun main() {
    startKoin {
        slf4jLogger()
        modules(
            configHandlerModule(),
            daoModule(),
            tgBotModule(),
            loginModule(),
        )

    }
    val dbHandler by inject<DatabaseHandler>()
    val handler by inject<ConfigHandler>()

    dbHandler.init()

    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
        configureSerialization()
        loginRouting(configHandler = handler)
    }.start(wait = true)
}
