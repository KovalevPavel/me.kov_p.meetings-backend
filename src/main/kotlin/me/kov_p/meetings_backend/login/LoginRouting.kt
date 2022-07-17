package me.kov_p.meetings_backend.login

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import java.util.UUID
import me.kov_p.meetings_backend.ConfigHandler
import me.kov_p.meetings_backend.database.dao.user.UserDao
import me.kov_p.meetings_backend.plugins.parseResponse
import org.koin.java.KoinJavaComponent.inject

fun Application.loginRouting() {
    routing {
        get(ConfigHandler.loginSubPath) {
            val userDao: UserDao by inject(UserDao::class.java)

            val receive = call.parseResponse(LoginReceiveRemote::class)

            if (userDao.isUserRegistered(receive.userName.orEmpty())) {
                call.respond(HttpStatusCode.Accepted, LoginRespondRemote(token = UUID.randomUUID().toString()))
            } else {
                call.respond(HttpStatusCode.BadRequest, "User is unregistered")
            }
        }
    }
}