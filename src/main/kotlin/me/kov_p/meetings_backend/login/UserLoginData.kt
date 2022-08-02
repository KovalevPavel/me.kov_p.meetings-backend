package me.kov_p.meetings_backend.login

data class UserLoginData(
    val userLogin: String,
    val generatedCode: Int,
    val createdTime: Long,
) {
    override fun equals(other: Any?): Boolean {
        return if (other is UserLoginData) userLogin == other.userLogin
        else super.equals(other)
    }
}
