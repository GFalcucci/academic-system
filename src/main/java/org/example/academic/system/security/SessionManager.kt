package org.example.academic.system.security

import org.example.academic.system.model.User

class SessionManager {
    var loggedUser: User? = null
        private set

    fun login(user: User?) {
        this.loggedUser = user
    }

    fun logout() {
        this.loggedUser = null
    }

    val isAuthenticated: Boolean
        get() = loggedUser != null
}