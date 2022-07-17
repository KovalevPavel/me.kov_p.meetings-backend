package me.kov_p.meetings_backend.plugins

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.request.receiveStream
import io.ktor.server.request.receiveText
import kotlin.reflect.KClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

internal suspend inline fun <reified T : Any> ApplicationCall.parseResponse(clazz: KClass<T>): T {
//    return withContext(Dispatchers.IO) {
        val respond = this@parseResponse.receive<JsonElement>()
        println("respond -> $respond")
        return try {
            Gson().fromJson(
                respond,
                clazz.java
            )
        } catch (e: Exception) {
            println("Can't convert respond $respond")
            throw e
        }
//    }
}
