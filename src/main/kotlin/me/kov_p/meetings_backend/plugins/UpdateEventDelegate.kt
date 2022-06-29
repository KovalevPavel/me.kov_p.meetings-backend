package me.kov_p.meetings_backend.plugins

import me.kov_p.meetings_backend.telegram_bot.UpdateVo

interface UpdateEventDelegate {
    fun isDelegateValid(updateVo: UpdateVo): Boolean
    fun handleUpdate(updateVo: UpdateVo)
}
