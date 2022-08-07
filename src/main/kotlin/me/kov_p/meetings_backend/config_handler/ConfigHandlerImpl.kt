package me.kov_p.meetings_backend.config_handler

class ConfigHandlerImpl: ConfigHandler {
    override val loginSubPath: String = System.getenv("loginUrl")
}