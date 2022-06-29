package me.kov_p.meetings_backend.login

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import java.util.UUID

fun Application.loginRouting() {
    routing {
        post("/login") {
            val receive = call.receiveParameters()
            receive["login"] ?: call.respond(HttpStatusCode.BadRequest, "Login is empty")
            val password = receive["password"] ?: call.respond(HttpStatusCode.BadRequest, "Password is empty")

            if (password.toString().length >=5) {
                call.respond(LoginRespondRemote(token = UUID.randomUUID().toString()))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Password is empty")
            }
        }
    }
}