package org.example.academic.system.model

class User(@JvmField val username: String, val password: String?, @JvmField val role: Role?) {
    override fun equals(obj: Any?): Boolean {
        if (this === obj) return true
        if (obj == null || javaClass != obj.javaClass) return false
        val other = obj as User
        return username == other.username
    }

    override fun hashCode(): Int {
        return username.hashCode()
    }
}