package me.kov_p.meetings_backend.login

import kotlin.random.Random

interface VerificationCodeHandler {
    fun requestLoginCode(userLogin: String): Int?
    fun checkLoginCode(data: UserLoginData): Boolean
}

class VerificationCodeHandlerImpl : VerificationCodeHandler {
    private val loginList = mutableSetOf<UserLoginData>()

    override fun requestLoginCode(userLogin: String): Int? {
        println("handler hash -> ${this.hashCode()}")
        return when (canGenerateCode(userLogin = userLogin)) {
            false -> {
                null
            }
            else -> {
                println("list -> $loginList")
                Random.nextInt(MIN_CODE_VALUE, MAX_CODE_VALUE)
                    .let { loginCode ->
                        loginList.add(
                            UserLoginData(
                                userLogin = userLogin,
                                generatedCode = loginCode,
                                createdTime = System.currentTimeMillis(),
                            )
                        )
                        println("list after add -> $loginList")
                        loginCode
                    }
            }
        }
    }

    override fun checkLoginCode(data: UserLoginData): Boolean {
        return loginList.any { it == data }
    }

    private fun canGenerateCode(userLogin: String): Boolean {
        println("finding user data...")
        val userData = loginList.firstOrNull { it.userLogin == userLogin } ?: return true
        println("user data -> $userData\ncurrent time = ${System.currentTimeMillis()}")
        return userData.createdTime <= System.currentTimeMillis() + NEW_CODE_GENERATE_DELAY_MS
    }

    companion object {
        private const val MIN_CODE_VALUE = 1000
        private const val MAX_CODE_VALUE = 9999
        private const val NEW_CODE_GENERATE_DELAY_MS = 30_000L
    }
}