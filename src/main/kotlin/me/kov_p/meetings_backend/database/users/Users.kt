package me.kov_p.meetings_backend.database.users

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object Users : Table("users") {
    private const val COLUMN_LOGIN = "COLUMN_LOGIN"
    private const val COLUMN_PASSWORD = "COLUMN_PASSWORD"
    private const val COLUMN_EMAIL = "COLUMN_EMAIL"
    private const val COLUMN_TELEGRAM = "COLUMN_TELEGRAM"

    private val login = Users.varchar(COLUMN_LOGIN, 25)
    private val password = Users.varchar(COLUMN_PASSWORD, 25)
    private val email = Users.varchar(COLUMN_EMAIL, 25)
    private val telegram = Users.varchar(COLUMN_TELEGRAM, 25)

    fun insert(userDTO: UserDto) {
        transaction {
            Users.insert {
                it[login] = userDTO.login
                it[password] = userDTO.password
                it[email] = userDTO.email
                it[telegram] = userDTO.telegram
            }
        }
    }

    fun update(userDTO: UserDto) {
        transaction {
            Users.update {
                it[login] = userDTO.login
                it[password] = userDTO.password
                it[email] = userDTO.email
                it[telegram] = userDTO.telegram
            }
        }
    }

    fun fetchUser(login: String): UserDto {
        val userModel = Users.select { Users.login.eq(login) }.single()
        return UserDto(
            login = userModel[Users.login],
            password = userModel[password],
            email = userModel[email],
            telegram = userModel[telegram]
        )
    }

    fun isLoginFree(login: String): Boolean = try {
        fetchUser(login)
        false
    } catch (e: Exception) {
        true
    }
}

