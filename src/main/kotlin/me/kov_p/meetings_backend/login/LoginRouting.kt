package me.kov_p.meetings_backend.login

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import me.kov_p.meetings_backend.ConfigHandler
import me.kov_p.meetings_backend.database.dao.user.UserDao
import org.koin.java.KoinJavaComponent.inject

fun Application.loginRouting() {
    routing {
        post(ConfigHandler.loginSubPath) {
            val requestCode: GenerateCodeInteractor by inject(GenerateCodeInteractor::class.java)
            val userDao: UserDao by inject(UserDao::class.java)

            val userName = call.receive<LoginReceiveRemote>().userName.orEmpty()

            if (userDao.isUserRegistered(userName)) {
                requestCode(userName)?.let {
                    call.respond(
                        status = HttpStatusCode.Accepted,
                        message = LoginRespondRemote(code = it)
                    )
                }
                return@post
            }

            call.respond(
                status = HttpStatusCode.Forbidden,
                message = LoginRespondRemote(errorMessage = "Unable to generate code")
            )
        }
    }
}