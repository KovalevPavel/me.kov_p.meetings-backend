package me.kov_p.meetings_backend.database.di

import me.kov_p.meetings_backend.database.dao.user.UserDao
import org.koin.dsl.module

fun daoModule() = module {
    single { UserDao() }
}