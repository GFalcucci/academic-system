import org.example.academic.system.controller.AuthenticationController
import org.example.academic.system.exception.AuthenticationException
import org.example.academic.system.repository.UserRepository
import org.example.academic.system.security.SessionManager
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class AuthenticationControllerTest {
    @Test
    @Throws(AuthenticationException::class)
    fun shouldAuthenticateValidUser() {
        val repository =
            UserRepository()

        val session =
            SessionManager()

        val controller =
            AuthenticationController(
                repository,
                session
            )

        val result =
            controller.login(
                "admin",
                "123"
            )

        Assertions.assertTrue(result)
    }

    @Test
    fun shouldThrowExceptionForInvalidUser() {
        val repository =
            UserRepository()

        val session =
            SessionManager()

        val controller =
            AuthenticationController(
                repository,
                session
            )

        Assertions.assertThrows<AuthenticationException?>(
            AuthenticationException::class.java,
            Executable {
                controller.login(
                    "naoExiste",
                    "123"
                )
            })
    }

    @Test
    fun shouldThrowExceptionForInvalidPassword() {
        val repository =
            UserRepository()

        val session =
            SessionManager()

        val controller =
            AuthenticationController(
                repository,
                session
            )

        Assertions.assertThrows<AuthenticationException?>(
            AuthenticationException::class.java,
            Executable {
                controller.login(
                    "admin",
                    "senhaErrada"
                )
            })
    }
}
