package me.kov_p.meetings_backend.telegram_bot.update_delegates

import me.kov_p.meetings_backend.database.dao.user.UserDao
import me.kov_p.meetings_backend.telegram_bot.UpdateVo
import org.koin.java.KoinJavaComponent.inject

class NewMessageUpdateDelegate : UpdateEventDelegate {
    private val userDao by inject<UserDao>(UserDao::class.java)

    override fun isDelegateValid(updateVo: UpdateVo): Boolean {
        return updateVo is UpdateVo.NewMessage
    }

    override fun handleUpdate(updateVo: UpdateVo) {
        when (updateVo) {
            is UpdateVo.NewMessage -> {
//                TODO("Add new message handling")
            }
            else -> return
        }
    }
}