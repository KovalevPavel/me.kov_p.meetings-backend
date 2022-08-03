package me.kov_p.meetings_backend.database.dao.user

import me.kov_p.meetings_backend.database.models.UserDto
import org.jetbrains.exposed.sql.ResultRow
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

    fun getUser(userName: String): UserDto? {
        return transaction {
            Users.select {
                Users.userName.eq(userName)
            }
                .firstOrNull()
                ?.toUser()
        }
    }

    fun deleteUser(userIdToDelete: Long) {
        transaction {
            Users.deleteWhere {
                Users.userChatId.eq(userIdToDelete)
            }
        }
    }

    private fun ResultRow.toUser(): UserDto = UserDto(
        userName = this[Users.userName],
        chatId = this[Users.userChatId]
    )
}