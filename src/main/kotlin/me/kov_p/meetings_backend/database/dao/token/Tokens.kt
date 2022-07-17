package me.kov_p.meetings_backend.database.dao.token

import me.kov_p.meetings_backend.database.models.TokenDto
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

internal object Tokens: Table("tokens") {
    private val id = Tokens.varchar("id", 25)
    private val login = Tokens.varchar("login", 25)
    private val token = Tokens.varchar("token", 50)

    fun insert(tokenDto: TokenDto) {
        transaction {
            Tokens.insert {
                it[id] = tokenDto.rowId
                it[login] = tokenDto.login
                it[token] = tokenDto.token
            }
        }
    }
}
