package me.kov_p.meetings_backend.database.dao.user

import me.kov_p.meetings_backend.database.models.UserDto
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserDao {
    fun createUser(newUser: UserDto) {
        transaction {
            Users.insert {
                it[userName] = newUser.userName
                it[userChatId] = newUser.chatId
            }
        }
    }

    fun deleteUser(userIdToDelete: Int) {
        transaction {
            Users.deleteWhere {
                Users.userChatId.eq(userIdToDelete)
            }
        }
    }

    fun getUserByName(name: String): UserDto {
        val userModel = Users.select { Users.userName.eq(name) }.single()
        return UserDto(
            userName = userModel[Users.userName],
            chatId = userModel[Users.userChatId]
        )
    }
}