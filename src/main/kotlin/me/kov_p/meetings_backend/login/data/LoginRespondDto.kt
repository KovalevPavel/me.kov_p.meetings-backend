package me.kov_p.meetings_backend.login.data

data class LoginRespondDto(
    val errorMessage: String = "",
    val token: String? = null,
)
