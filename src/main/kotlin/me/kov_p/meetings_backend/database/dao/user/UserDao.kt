package me.kov_p.meetings_backend.database.dao.user

import me.kov_p.meetings_backend.database.models.UserDto

interface UserDao {
    fun createUser(newUser: UserDto)
    fun getUserByName(name: String): UserDto
}