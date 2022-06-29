package me.kov_p.meetings_backend.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRespondRemote(
    val token: String
)
