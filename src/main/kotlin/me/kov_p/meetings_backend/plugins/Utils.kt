package me.kov_p.meetings_backend.plugins

import com.google.gson.Gson
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receiveStream
import kotlin.reflect.KClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal suspend inline fun <reified T : Any> ApplicationCall.parseResponse(clazz: KClass<T>): T {
    return withContext(Dispatchers.IO) {
        val respond = this@parseResponse.receiveStream().bufferedReader().use {
            it.readText()
        }
        println("respond -> $respond")
        try {
            Gson().fromJson(
                respond,
                clazz.java
            )
        } catch (e: Exception) {
            println("Can't convert respond $respond")
            throw e
        }
    }
}
