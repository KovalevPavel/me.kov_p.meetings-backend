package me.kov_p.meetings_backend.database.di

import me.kov_p.meetings_backend.database.DatabaseHandler
import me.kov_p.meetings_backend.database.dao.TgBotDao
import me.kov_p.meetings_backend.database.dao.TokenDao
import me.kov_p.meetings_backend.database.dao.UserDao
import org.koin.dsl.module

fun daoModule() = module {
    single { UserDao() }
    single { TokenDao() }
    single { DatabaseHandler() }
    single { TgBotDao() }
}
