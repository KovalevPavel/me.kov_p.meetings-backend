package me.kov_p.meetings_backend.database

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.HoconApplicationConfig
import org.jetbrains.exposed.sql.Database

object DatabaseHandler {
    private const val DB_URL_PATH = "db.jdbcUrl"
    private const val DB_USER_PATH = "db.dbUser"
    private const val DB_PASSWORD_PATH = "db.dbPassword"
    private const val DRIVER_CLASS_NAME = "org.postgresql.Driver"
    private const val TRANSACTION_ISOLATION = "TRANSACTION_REPEATABLE_READ"
    private const val MAX_POOL_SIZE = 3

    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val dbUrl = appConfig.property(DB_URL_PATH).getString()
    private val dbUser = appConfig.property(DB_USER_PATH).getString()
    private val dbPassword = appConfig.property(DB_PASSWORD_PATH).getString()

    fun init() {
        Database.connect(hikari())
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig().apply {
            driverClassName = DRIVER_CLASS_NAME
            jdbcUrl = dbUrl
            username = dbUser
            password = dbPassword
            maximumPoolSize = MAX_POOL_SIZE
            isAutoCommit = false
            transactionIsolation = TRANSACTION_ISOLATION
            validate()
        }
        return HikariDataSource(config)
    }
}