import org.example.academic.system.exception.AcademicSystemException
import org.example.academic.system.model.AcademicClass
import org.example.academic.system.model.PracticalAssignment
import org.example.academic.system.repository.AcademicClassRepository
import org.example.academic.system.service.AssessmentService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class AssessmentServiceTest {
    @Test
    fun shouldRegisterAssessment() {
        val repository =
            AcademicClassRepository()

        val service =
            AssessmentService(
                repository
            )

        val academicClass =
            AcademicClass(
                "POO001",
                "Programação Orientada a Objetos"
            )

        val assessment =
            PracticalAssignment(
                "TP1",
                30.0,
                100.0,
                "Java"
            )

        service.registerAssessment(
            academicClass,
            assessment
        )

        Assertions.assertEquals(
            1,
            academicClass.assessments.size
        )
    }

    @Test
    fun shouldStoreAssessmentInClass() {
        val repository =
            AcademicClassRepository()

        val service =
            AssessmentService(
                repository
            )

        val academicClass =
            AcademicClass(
                "POO001",
                "Programação Orientada a Objetos"
            )

        val assessment =
            PracticalAssignment(
                "TP1",
                30.0,
                100.0,
                "Java"
            )

        service.registerAssessment(
            academicClass,
            assessment
        )

        Assertions.assertTrue(
            academicClass.assessments
                .contains(assessment)
        )
    }

    @Test
    fun shouldThrowExceptionForInvalidAssessment() {
        val repository =
            AcademicClassRepository()

        val service =
            AssessmentService(
                repository
            )

        val academicClass =
            AcademicClass(
                "POO001",
                "Programação Orientada a Objetos"
            )

        Assertions.assertThrows<AcademicSystemException?>(
            AcademicSystemException::class.java,
            Executable {
                service.registerAssessment(
                    academicClass,
                    null
                )
            })
    }

    @Test
    fun shouldThrowExceptionForNonexistentClass() {
        val repository =
            AcademicClassRepository()

        val service =
            AssessmentService(
                repository
            )

        val assessment =
            PracticalAssignment(
                "TP1",
                30.0,
                100.0,
                "Java"
            )

        Assertions.assertThrows<AcademicSystemException?>(
            AcademicSystemException::class.java,
            Executable {
                service.registerAssessment(
                    null,
                    assessment
                )
            })
    }
}