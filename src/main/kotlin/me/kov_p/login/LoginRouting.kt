package me.kov_p.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

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