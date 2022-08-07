package me.kov_p.meetings_backend.login.domain

data class UserLoginData(
    val userLogin: String,
    val generatedCode: Int,
    val createdTime: Long,
)
