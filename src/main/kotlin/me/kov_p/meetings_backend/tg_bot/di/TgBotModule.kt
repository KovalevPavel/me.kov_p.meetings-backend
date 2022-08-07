package me.kov_p.meetings_backend.tg_bot.di

import me.kov_p.meetings_backend.tg_bot.data.BotHandlerImpl
import me.kov_p.meetings_backend.tg_bot.domain.BotHandler
import org.koin.dsl.module

fun tgBotModule() = module {
    single<BotHandler> { BotHandlerImpl(botDao = get()) }
}