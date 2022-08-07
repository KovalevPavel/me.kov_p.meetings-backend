package me.kov_p.meetings_backend.database.dao

import me.kov_p.meetings_backend.database.models.User
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserDao {
    fun getUser(userName: String): User? {
        return transaction {
            Users.select {
                Users.userName.eq(userName)
            }
                .firstOrNull()
                ?.toUser()
        }
    }

    private fun ResultRow.toUser(): User = User(
        userName = this[Users.userName],
        chatId = this[Users.userChatId]
    )
}

internal object Users : Table("users") {
    private const val COLUMN_USERNAME = "user_name"
    private const val COLUMN_CHAT_ID = "chat_id"

    val userName = Users.varchar(COLUMN_USERNAME, 30)
    val userChatId = Users.long(COLUMN_CHAT_ID)
}