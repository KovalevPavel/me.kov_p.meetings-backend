package me.kov_p.meetings_backend.login

import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.random.Random

interface VerificationCodeHandler {
    fun requestLoginCode(userLogin: String): Int?
    fun checkLoginCode(data: UserLoginData): Boolean
}

class VerificationCodeHandlerImpl : VerificationCodeHandler {
    private val loginList = mutableSetOf<UserLoginData>()

    override fun requestLoginCode(userLogin: String): Int? {
        println("generating code for $userLogin")
        return when (canGenerateCode(userLogin = userLogin)) {
            false -> {
                println("Can't generate code")
                null
            }
            else -> {
                println("Generating code")
                Random.nextInt(MIN_CODE_VALUE, MAX_CODE_VALUE)
                    .let { loginCode ->
                        val loginData = UserLoginData(
                            userLogin = userLogin,
                            generatedCode = loginCode
                        )
                            .also(loginList::add)
                        Timer().schedule(delay = 0, period = NEW_CODE_GENERATE_DELAY_MS) {
                            loginList.remove(loginData)
                        }
                        loginCode
                    }
            }
        }
    }

    override fun checkLoginCode(data: UserLoginData): Boolean {
        return loginList.any { it == data }
    }

    private fun canGenerateCode(userLogin: String): Boolean =
        loginList.none { it.userLogin == userLogin }

    companion object {
        private const val MIN_CODE_VALUE = 1000
        private const val MAX_CODE_VALUE = 9999
        private const val NEW_CODE_GENERATE_DELAY_MS = 30_000L
    }
}