package me.kov_p.meetings_backend.login

data class LoginRespondRemote(
    val errorMessage: String = "",
    val token: String? = null,
)
