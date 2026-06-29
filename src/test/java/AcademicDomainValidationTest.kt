import org.example.academic.system.exception.InvalidAcademicClassException
import org.example.academic.system.exception.InvalidAssessmentException
import org.example.academic.system.model.AcademicClass
import org.example.academic.system.model.Exam
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.junit.jupiter.api.function.ThrowingSupplier

/**
 * TUS-2385 - Test academic domain validation
 */
class AcademicDomainValidationTest {
    @Test
    fun validClassMustPassValidation() {
        Assertions.assertDoesNotThrow<AcademicClass?>(ThrowingSupplier {
            AcademicClass(
                "POO001",
                "Programação Orientada a Objetos"
            )
        })
    }

    @Test
    fun classWithBlankCodeMustFailValidation() {
        Assertions.assertThrows<InvalidAcademicClassException?>(
            InvalidAcademicClassException::class.java,
            Executable { AcademicClass("", "Programação") })
    }

    @Test
    fun classWithNullCodeMustFailValidation() {
        Assertions.assertThrows<InvalidAcademicClassException?>(
            InvalidAcademicClassException::class.java,
            Executable { AcademicClass("", "Programação") })
    }

    @Test
    fun classWithBlankTitleMustFailValidation() {
        Assertions.assertThrows<InvalidAcademicClassException?>(
            InvalidAcademicClassException::class.java,
            Executable { AcademicClass("POO001", "") })
    }

    @Test
    fun validAssessmentMustPassValidation() {
        Assertions.assertDoesNotThrow<Exam?>(ThrowingSupplier { Exam("Prova 1", 30.0, 100.0) })
    }

    @Test
    fun assessmentWithInvalidValueMustFailValidation() {
        Assertions.assertThrows<InvalidAssessmentException?>(
            InvalidAssessmentException::class.java,
            Executable { Exam("Prova 1", 30.0, -5.0) })
    }

    @Test
    fun assessmentWithInvalidWeightMustFailValidation() {
        Assertions.assertThrows<InvalidAssessmentException?>(
            InvalidAssessmentException::class.java,
            Executable { Exam("Prova 1", 0.0, 100.0) })
    }

    @Test
    fun assessmentWithBlankNameMustFailValidation() {
        Assertions.assertThrows<InvalidAssessmentException?>(
            InvalidAssessmentException::class.java,
            Executable { Exam("", 30.0, 100.0) })
    }
}