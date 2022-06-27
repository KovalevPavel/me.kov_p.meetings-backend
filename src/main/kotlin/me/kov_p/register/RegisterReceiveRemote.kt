package me.kov_p.register

@kotlinx.serialization.Serializable
data class RegisterReceiveRemote(
    val login: String,
    val password: String
)
