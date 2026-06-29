import org.example.academic.system.exception.AcademicSystemException
import org.example.academic.system.model.AcademicClass
import org.example.academic.system.repository.AcademicClassRepository
import org.example.academic.system.service.ClassService
import org.example.academic.system.service.ClassServiceImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.function.Executable

class ClassServiceTest {
    @Test
    fun shouldRegisterAcademicClass() {
        val repository =
            AcademicClassRepository()

        val service: ClassService =
            ClassServiceImpl(
                repository
            )

        val academicClass =
            AcademicClass(
                "POO001",
                "Programação Orientada a Objetos"
            )

        service.registerClass(
            academicClass
        )

        assertEquals(
            1,
            service.allClasses.size
        )
    }

    @Test
    fun shouldStoreClassInRepository() {
        val repository =
            AcademicClassRepository()

        val service: ClassService =
            ClassServiceImpl(
                repository
            )

        val academicClass =
            AcademicClass(
                "POO001",
                "Programação Orientada a Objetos"
            )

        service.registerClass(
            academicClass
        )

        Assertions.assertTrue(
            repository.findAll()
                .contains(
                    academicClass
                )
        )
    }

    @Test
    fun shouldThrowExceptionForInvalidClass() {
        Assertions.assertThrows<AcademicSystemException?>(
            AcademicSystemException::class.java,
            Executable {
                AcademicClass(
                    "",
                    "Programação Orientada a Objetos"
                )
            })
    }
}