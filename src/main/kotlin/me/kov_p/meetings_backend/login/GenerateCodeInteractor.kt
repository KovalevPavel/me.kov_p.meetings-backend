package me.kov_p.meetings_backend.login

class GenerateCodeInteractor(
    private val codeHandler: VerificationCodeHandler,
) {
    suspend operator fun invoke(userLogin: String): Int? {
        return codeHandler.requestLoginCode(userLogin = userLogin)
    }
}