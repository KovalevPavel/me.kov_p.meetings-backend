package me.kov_p.meetings_backend.login.domain

interface VerificationCodeHandler {
    fun requestLoginCode(userLogin: String): Int?
    fun deleteGeneratedCode(userLogin: String)
    fun checkLoginCode(data: UserLoginData): Boolean
}
