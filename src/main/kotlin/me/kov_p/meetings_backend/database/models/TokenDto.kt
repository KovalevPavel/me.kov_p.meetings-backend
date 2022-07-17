package me.kov_p.meetings_backend.database.models

data class TokenDto(
    val rowId: String,
    val login: String,
    val token: String
)
