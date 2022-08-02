package me.kov_p.meetings_backend.login

import kotlin.random.Random

interface VerificationCodeHandler {
    fun requestLoginCode(userLogin: String): Int?
    fun checkLoginCode(data: UserLoginData): Boolean
}

class VerificationCodeHandlerImpl : VerificationCodeHandler {
    private val loginList = mutableSetOf<UserLoginData>()

    override fun requestLoginCode(userLogin: String): Int? {
        return when (canGenerateCode(userLogin = userLogin)) {
            false -> {
                null
            }
            else -> {
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
        val userData = loginList.firstOrNull { it.userLogin == userLogin } ?: return true
        return userData.createdTime + NEW_CODE_GENERATE_DELAY_MS <= System.currentTimeMillis()
    }

    companion object {
        private const val MIN_CODE_VALUE = 1000
        private const val MAX_CODE_VALUE = 9999
        private const val NEW_CODE_GENERATE_DELAY_MS = 30_000L
    }
}