import org.example.academic.system.logging.ApplicationLogger.debug
import org.example.academic.system.logging.ApplicationLogger.error
import org.example.academic.system.logging.ApplicationLogger.info
import org.example.academic.system.logging.ApplicationLogger.warn
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

/**
 * TUS-2395 - Verify logging infrastructure behavior
 */
class LoggingInfrastructureTest {
    @Test
    fun loggerInstanceCanBeCreatedSuccessfully() {
        Assertions.assertDoesNotThrow(Executable {
            Class.forName(
                "org.example.academic.system.logging.ApplicationLogger"
            )
        })
    }

    @Test
    fun infoLogCanBeWrittenWithoutException() {
        Assertions.assertDoesNotThrow(Executable { info("Test info message") })
    }

    @Test
    fun warnLogCanBeWrittenWithoutException() {
        Assertions.assertDoesNotThrow(Executable { warn("Test warn message") })
    }

    @Test
    fun errorLogCanBeWrittenWithoutException() {
        Assertions.assertDoesNotThrow(Executable { error("Test error message") })
    }

    @Test
    fun debugLogCanBeWrittenWithoutException() {
        Assertions.assertDoesNotThrow(Executable { debug("Test debug message") })
    }
}