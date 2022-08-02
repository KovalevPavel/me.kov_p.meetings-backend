package me.kov_p.meetings_backend.login.di

import me.kov_p.meetings_backend.login.GenerateCodeInteractor
import me.kov_p.meetings_backend.login.VerificationCodeHandler
import me.kov_p.meetings_backend.login.VerificationCodeHandlerImpl
import org.koin.dsl.module

fun loginModule() = module {
    single<VerificationCodeHandler> { VerificationCodeHandlerImpl() }
    single { GenerateCodeInteractor(codeHandler = get()) }
}