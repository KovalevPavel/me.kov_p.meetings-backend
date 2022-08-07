package me.kov_p.meetings_backend.database.dao

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.kov_p.meetings_backend.database.models.Token
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class TokenDao {
    suspend fun getToken(userName: String): String? {
        return withContext(Dispatchers.IO) {
            transaction {
                Tokens.select {
                    Tokens.login.eq(userName)
                }
                    .firstOrNull()
                    ?.toToken()
            }
        }
    }

    private fun ResultRow.toToken(): String = this[Tokens.token]
}

private object Tokens : Table("tokens") {
    val login = Tokens.varchar("user_name", 25)
    val token = Tokens.varchar("token", 75)

    fun insert(tokenDto: Token) {
        transaction {
            Tokens.insert {
                it[login] = tokenDto.login
                it[token] = tokenDto.token
            }
        }
    }
}
