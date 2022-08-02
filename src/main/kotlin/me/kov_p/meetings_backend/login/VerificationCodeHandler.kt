package me.kov_p.meetings_backend.login

import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.random.Random
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface VerificationCodeHandler {
    suspend fun requestLoginCode(userLogin: String): Int?
    fun checkLoginCode(data: UserLoginData): Boolean
}

class VerificationCodeHandlerImpl : VerificationCodeHandler {
    private val loginList = mutableSetOf<UserLoginData>()

    override suspend fun requestLoginCode(userLogin: String): Int? {
        return withContext(Dispatchers.IO) {
            when (canGenerateCode(userLogin = userLogin)) {
                false -> {
                    null
                }
                else -> {
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
    }

    override fun checkLoginCode(data: UserLoginData): Boolean {
        return loginList.any { it == data }
    }

    private fun canGenerateCode(userLogin: String): Boolean =
        loginList.any { it.userLogin == userLogin }

    companion object {
        private const val MIN_CODE_VALUE = 1000
        private const val MAX_CODE_VALUE = 9999
        private const val NEW_CODE_GENERATE_DELAY_MS = 30_000L
    }
}