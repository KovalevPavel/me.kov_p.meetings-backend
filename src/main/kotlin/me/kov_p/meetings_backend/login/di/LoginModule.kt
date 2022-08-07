package me.kov_p.meetings_backend.login.di

import me.kov_p.meetings_backend.login.domain.interactors.DeleteGeneratedCodeInteractor
import me.kov_p.meetings_backend.login.domain.interactors.GenerateCodeInteractor
import me.kov_p.meetings_backend.login.domain.interactors.SendCodeToUserInteractor
import me.kov_p.meetings_backend.login.domain.VerificationCodeHandler
import me.kov_p.meetings_backend.login.data.VerificationCodeHandlerImpl
import me.kov_p.meetings_backend.login.domain.interactors.GetSavedUserTokenDelegate
import org.koin.dsl.module

fun loginModule() = module {
    single<VerificationCodeHandler> { VerificationCodeHandlerImpl() }
    single { GenerateCodeInteractor(codeHandler = get()) }
    single { DeleteGeneratedCodeInteractor(codeHandler = get()) }
    single { SendCodeToUserInteractor(botHandler = get()) }
    single { GetSavedUserTokenDelegate(tokenDao = get()) }
}