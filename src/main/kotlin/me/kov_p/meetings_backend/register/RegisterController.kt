package me.kov_p.meetings_backend.register

import me.kov_p.meetings_backend.database.users.UserDto
import me.kov_p.meetings_backend.database.users.Users

class RegisterController {
    fun registerNewUser(registerReceiveRemote: RegisterReceiveRemote): Result<String> {
        Users.isLoginFree(login = registerReceiveRemote.login)
            .let {
                if (!it) return Result.failure(RuntimeException("User exists"))
            }
        return try {
            Users.insert(
                userDTO = UserDto(
                    login = registerReceiveRemote.login,
                    password = registerReceiveRemote.password
                )
            )
            Result.success("")
        } catch (e: Exception) {
            Result.failure(RuntimeException("Can't create new user"))
        }
    }

    private fun generateToken(login: String) {
        
    }
}