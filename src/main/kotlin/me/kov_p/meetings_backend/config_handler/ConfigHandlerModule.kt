package me.kov_p.meetings_backend.config_handler

import org.koin.dsl.module

fun configHandlerModule() = module {
    single<ConfigHandler> { ConfigHandlerImpl() }
}