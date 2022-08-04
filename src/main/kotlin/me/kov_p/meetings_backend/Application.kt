package me.kov_p.meetings_backend

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import me.kov_p.meetings_backend.database.DatabaseHandler
import me.kov_p.meetings_backend.database.di.daoModule
import me.kov_p.meetings_backend.login.di.loginModule
import me.kov_p.meetings_backend.login.loginRouting
import me.kov_p.meetings_backend.plugins.configureRouting
import me.kov_p.meetings_backend.plugins.configureSerialization
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main() {
    DatabaseHandler.init()

    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
        configureSerialization()
        installDi()
        configureRouting()
        loginRouting()
    }.start(wait = true)
}

fun Application.installDi() {
    install(Koin) {
        slf4jLogger()
        modules(
            daoModule(),
            loginModule(),
        )
    }
}
