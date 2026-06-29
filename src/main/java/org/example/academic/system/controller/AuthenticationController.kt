package org.example.academic.system.controller

import org.example.academic.system.exception.AuthenticationException
import org.example.academic.system.logging.ApplicationLogger
import org.example.academic.system.model.User
import org.example.academic.system.repository.UserRepository
import org.example.academic.system.security.SessionManager

class AuthenticationController(
    private val repository: UserRepository,
    private val session: SessionManager
) {

    @Throws(AuthenticationException::class)
    fun login(username: String?, password: String?): Boolean {
        val user = repository.findByUsername(username)
            ?: throw AuthenticationException("Usuário não encontrado.").also {
                ApplicationLogger.warn("LOGIN_FAILED - user not found: $username")
            }

        if (user.password != password) {
            ApplicationLogger.warn("LOGIN_FAILED - wrong password for user: $username")
            throw AuthenticationException("Senha inválida.")
        }

        session.login(user)
        ApplicationLogger.info("LOGIN_SUCCESS - user: $username role: ${user.role}")

        return true
    }

    fun logout() {
        val user = session.loggedUser
        user?.let {
            ApplicationLogger.info("LOGOUT - user: ${it.username}")
        }
        session.logout()
    }

    val loggedUser: User?
        get() = session.loggedUser
}