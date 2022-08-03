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
import me.kov_p.meetings_backend.utils.inject
import org.koin.java.KoinJavaComponent.inject

fun Application.loginRouting() {
    routing {
        post(ConfigHandler.loginSubPath) {
            val generateCode by inject<GenerateCodeInteractor>()
            val deleteCode by inject<DeleteGeneratedCodeInteractor>()
            val sendCode by inject<SendCodeToUserInteractor>()

            val userDao: UserDao by inject(UserDao::class.java)

            val userName = call.receive<LoginReceiveRemote>()
                .userName
                .orEmpty()
                .removePrefix("@")

            when (userDao.isUserRegistered(userName)) {
                false -> {
                    call.respond(
                        status = HttpStatusCode.Unauthorized,
                        message = LoginRespondRemote(errorMessage = System.getenv(USER_IS_UNREGISTERED_MESSAGE_KEY))
                    )
                }

                true -> {
                    generateCode(userName)?.let { code ->
                        call.respond(
                            status = HttpStatusCode.Accepted,
                            message = LoginRespondRemote()
                        )

                        when (sendCode(userLogin = userName, confirmationCode = code)) {
                            true -> {
                                //do nothing
                            }

                            false -> {
                                deleteCode(userLogin = userName)
                                call.respond(
                                    status = HttpStatusCode.BadRequest,
                                    message = LoginRespondRemote(errorMessage = System.getenv(BOT_NOT_ADDED_MESSAGE_KEY))
                                )
                            }
                        }
                    }
                        ?: kotlin.run {
                            call.respond(
                                status = HttpStatusCode.Locked,
                                message = LoginRespondRemote(errorMessage = "Timeout!")
                            )
                        }
                }
            }
        }
    }
}