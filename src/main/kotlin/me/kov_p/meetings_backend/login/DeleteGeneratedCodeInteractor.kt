package me.kov_p.meetings_backend.login

class DeleteGeneratedCodeInteractor(
    private val codeHandler: VerificationCodeHandler,
) {
    operator fun invoke(userLogin: String) {
        codeHandler.deleteGeneratedCode(userLogin = userLogin)
    }
}
