package me.kov_p.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRespondRemote(
    val token: String
)
