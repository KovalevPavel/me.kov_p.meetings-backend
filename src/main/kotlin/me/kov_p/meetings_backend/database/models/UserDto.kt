package me.kov_p.meetings_backend.database.models

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val userName: String,
    val chatId: Long,
)
