package me.kov_p.meetings_backend.login

data class UserLoginData(
    val userLogin: String,
    val generatedCode: Int,
    val createdTime: Long,
)
