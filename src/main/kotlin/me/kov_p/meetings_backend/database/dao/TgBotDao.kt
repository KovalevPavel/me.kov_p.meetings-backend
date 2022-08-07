package me.kov_p.meetings_backend.database.dao

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class TgBotDao {
    fun getBotToken(): String {
        return transaction {
            TgBot.select {
                TgBot.token.isNotNull()
            }
                .single()
                .toBotToken()
        }
    }

    private fun ResultRow.toBotToken(): String = this[TgBot.token]
}

private object TgBot : Table("tg_bot") {
    val token = TgBot.varchar("token", 75)
}
