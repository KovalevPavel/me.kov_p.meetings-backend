package me.kov_p.meetings_backend.plugins

import com.google.gson.Gson
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receiveText
import kotlin.reflect.KClass

internal suspend inline fun <reified T : Any> ApplicationCall.parseResponse(clazz: KClass<T>): T {
    val respond = this.receiveText()

    return try {
        Gson().fromJson(
            respond,
            clazz.java
        )
    } catch (e: Exception) {
        println("Can't convert respond $respond")
        throw e
    }
}
