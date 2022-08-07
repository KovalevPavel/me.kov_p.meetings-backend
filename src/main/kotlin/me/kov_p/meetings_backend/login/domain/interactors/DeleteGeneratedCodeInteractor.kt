package me.kov_p.meetings_backend.login.domain.interactors

import me.kov_p.meetings_backend.login.domain.VerificationCodeHandler

class DeleteGeneratedCodeInteractor(
    private val codeHandler: VerificationCodeHandler,
) {
    operator fun invoke(userLogin: String) {
        codeHandler.deleteGeneratedCode(userLogin = userLogin)
    }
}
