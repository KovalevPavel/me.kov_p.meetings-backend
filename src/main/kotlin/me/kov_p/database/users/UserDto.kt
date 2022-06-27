package me.kov_p.database.users

class UserDto(
    val login: String,
    val password: String,
    val email: String = "",
    val telegram: String = ""
)
