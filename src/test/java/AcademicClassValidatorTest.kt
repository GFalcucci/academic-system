import org.example.academic.system.exception.InvalidAcademicClassException
import org.example.academic.system.exception.InvalidAssessmentException
import org.example.academic.system.model.AcademicClass
import org.example.academic.system.model.Exam
import org.example.academic.system.validation.AcademicClassValidator
import org.example.academic.system.validation.AssessmentValidator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

/**
 * TUS-2371 - Validate academic domain objects using Jakarta Bean Validation
 * TUS-2385 - Test academic domain validation
 */
class AcademicClassValidatorTest {
    private var classValidator: AcademicClassValidator? = null
    private var assessmentValidator: AssessmentValidator? = null

    @BeforeEach
    fun setUp() {
        classValidator = AcademicClassValidator()
        assessmentValidator = AssessmentValidator()
    }

    // --- AcademicClass ---
    @Test
    fun validClassMustPassValidation() {
        val ac = AcademicClass("POO001", "Programação OO")
        Assertions.assertDoesNotThrow(Executable { classValidator!!.validate(ac) })
    }

    @Test
    fun classWithBlankCodeMustFailValidation() {
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

    // --- Assessment ---
    @Test
    fun validAssessmentMustPassValidation() {
        val exam = Exam("Prova 1", 30.0, 100.0)
        Assertions.assertDoesNotThrow(Executable { assessmentValidator!!.validate(exam) })
    }

    @Test
    fun assessmentWithInvalidWeightMustFailValidation() {
        Assertions.assertThrows<InvalidAssessmentException?>(
            InvalidAssessmentException::class.java,
            Executable { Exam("Prova 1", 0.0, 100.0) })
    }

    @Test
    fun assessmentWithInvalidValueMustFailValidation() {
        Assertions.assertThrows<InvalidAssessmentException?>(
            InvalidAssessmentException::class.java,
            Executable { Exam("Prova 1", 30.0, -1.0) })
    }
}