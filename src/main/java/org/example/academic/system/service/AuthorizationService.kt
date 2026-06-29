package org.example.academic.system.service

import org.example.academic.system.exception.AuthorizationException
import org.example.academic.system.logging.ApplicationLogger.warn
import org.example.academic.system.model.Role
import org.example.academic.system.model.User

/**
 * TUS-2392 - Log authorization failures
 */
class AuthorizationService {
    fun isAdmin(user: User?): Boolean {
        return user != null
                && user.role == Role.ADMIN
    }

    fun isProfessor(user: User?): Boolean {
        return user != null
                && user.role == Role.PROFESSOR
    }

    @Throws(AuthorizationException::class)
    fun authorize(
        user: User?,
        requiredRole: Role?
    ) {
        if (user == null) {
            warn(
                ("AUTHORIZATION_FAILED - acesso nao autenticado"
                        + " tentou operacao que requer: "
                        + requiredRole)
            )

            throw AuthorizationException(
                "Usuário não autenticado."
            )
        }

        if (user.role != requiredRole) {
            warn(
                ("AUTHORIZATION_FAILED - usuario: "
                        + user.username
                        + " | role atual: " + user.role
                        + " | role exigida: " + requiredRole)
            )

            throw AuthorizationException(
                "Acesso negado."
            )
        }
    }
}