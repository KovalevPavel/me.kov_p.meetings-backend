package me.kov_p.meetings_backend.login.data

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import me.kov_p.meetings_backend.config_handler.ConfigHandler
import me.kov_p.meetings_backend.database.dao.UserDao
import me.kov_p.meetings_backend.login.BOT_NOT_ADDED_MESSAGE_KEY
import me.kov_p.meetings_backend.login.USER_IS_UNREGISTERED_MESSAGE_KEY
import me.kov_p.meetings_backend.login.domain.interactors.DeleteGeneratedCodeInteractor
import me.kov_p.meetings_backend.login.domain.interactors.GenerateCodeInteractor
import me.kov_p.meetings_backend.login.domain.interactors.GetSavedUserTokenDelegate
import me.kov_p.meetings_backend.login.domain.interactors.SendCodeToUserInteractor
import me.kov_p.meetings_backend.utils.inject

fun Application.loginRouting(configHandler: ConfigHandler) {
    routing {
        post(configHandler.loginSubPath) {
            val getSavedToken by inject<GetSavedUserTokenDelegate>()
            val generateCode by inject<GenerateCodeInteractor>()
            val deleteCode by inject<DeleteGeneratedCodeInteractor>()
            val sendCode by inject<SendCodeToUserInteractor>()

            val userDao by inject<UserDao>()

            val userName = call.receive<LoginReceiveDto>()
                .userName
                .orEmpty()
                .removePrefix("@")

            getSavedToken(userName = userName)?.let {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = LoginRespondDto(
                        token = it
                    )
                )
                return@post
            }

            when (val userFromDb = userDao.getUser(userName = userName)) {
                null -> {
                    call.respond(
                        status = HttpStatusCode.Unauthorized,
                        message = LoginRespondDto(
                            errorMessage = System.getenv(USER_IS_UNREGISTERED_MESSAGE_KEY)
                        )
                    )
                }

                else -> {
                    generateCode(userFromDb.userName)?.let { code ->
                        when (sendCode(chatId = userFromDb.chatId, confirmationCode = code)) {
                            true -> {
                                println("message sent")
                            }

                            false -> {
                                deleteCode(userLogin = userName)
                                call.respond(
                                    status = HttpStatusCode.BadRequest,
                                    message = LoginRespondDto(
                                        errorMessage = System.getenv(
                                            BOT_NOT_ADDED_MESSAGE_KEY
                                        )
                                    )
                                )
                                return@post
                            }
                        }
                        call.respond(
                            status = HttpStatusCode.Accepted,
                            message = LoginRespondDto()
                        )
                    }
                        ?: kotlin.run {
                            call.respond(
                                status = HttpStatusCode.Locked,
                                message = LoginRespondDto(errorMessage = "Timeout!")
                            )
                        }
                }
            }
        }
    }
}