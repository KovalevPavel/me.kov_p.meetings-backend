package me.kov_p.meetings_backend.telegram_bot.update_delegates

import me.kov_p.meetings_backend.database.dao.user.UserDao
import me.kov_p.meetings_backend.telegram_bot.UpdateVo
import org.koin.java.KoinJavaComponent.inject

class DeleteUserDelegate : UpdateEventDelegate {
    private val userDao: UserDao by inject(UserDao::class.java)

    override fun isDelegateValid(updateVo: UpdateVo): Boolean {
        return updateVo is UpdateVo.DeletedChatMember
    }

    override fun handleUpdate(updateVo: UpdateVo) {
        when (updateVo) {
            is UpdateVo.DeletedChatMember -> userDao.deleteUser(userIdToDelete = updateVo.id)
            else -> return
        }
    }
}