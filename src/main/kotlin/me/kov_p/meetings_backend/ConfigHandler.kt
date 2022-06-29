package me.kov_p.meetings_backend

object ConfigHandler {
    val botUpdateSubPath: String = System.getenv("botUpdateUrl")
    val botUpdateUrl: String
        get() = baseUrl.plus(botUpdateSubPath)

    private val baseUrl = System.getenv("appUrl")
}