package me.kov_p.meetings_backend

object ConfigHandler {
    val botUpdateSubPath: String = System.getenv("botUpdateUrl")
    val botUpdateUrl: String
        get() = baseUrl.plus(botUpdateSubPath)

    val loginSubPath: String = System.getenv("loginUrl")

    private val baseUrl = System.getenv("appUrl")
}