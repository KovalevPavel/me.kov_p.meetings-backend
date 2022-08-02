package me.kov_p.meetings_backend.login

data class LoginRespondRemote(
    val errorMessage: String = "",
    val code: Int? = null,
)
