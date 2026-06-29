import org.example.academic.system.exception.AuthorizationException
import org.example.academic.system.model.Role
import org.example.academic.system.model.User
import org.example.academic.system.service.AuthorizationService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class AuthorizationServiceTest {
    @Test
    @Throws(AuthorizationException::class)
    fun shouldAuthorizeAdmin() {
        val service =
            AuthorizationService()

        val admin =
            User(
                "admin",
                "123",
                Role.ADMIN
            )

        service.authorize(
            admin,
            Role.ADMIN
        )
    }

    @Test
    fun shouldDenyProfessorAccess() {
        val service =
            AuthorizationService()

        val professor =
            User(
                "professor",
                "123",
                Role.PROFESSOR
            )

        Assertions.assertThrows<AuthorizationException?>(
            AuthorizationException::class.java,
            Executable {
                service.authorize(
                    professor,
                    Role.ADMIN
                )
            })
    }

    @Test
    @Throws(AuthorizationException::class)
    fun shouldAuthorizeProfessorForProfessorRole() {
        val service =
            AuthorizationService()

        val professor =
            User(
                "professor",
                "123",
                Role.PROFESSOR
            )

        service.authorize(
            professor,
            Role.PROFESSOR
        )
    }
}