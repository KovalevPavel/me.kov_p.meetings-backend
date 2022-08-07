package me.kov_p.meetings_backend.login.domain.interactors

import me.kov_p.meetings_backend.database.dao.TokenDao

class GetSavedUserTokenDelegate(
    private val tokenDao: TokenDao,
) {
    suspend operator fun invoke(userName: String): String? {
        return tokenDao.getToken(userName = userName)
    }
}
