import org.example.academic.system.model.AcademicClass
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AcademicClassEqualityTest {
    @Test
    fun classesWithSameCodeMustBeEqual() {
        val c1 =
            AcademicClass("POO", "Programação")

        val c2 =
            AcademicClass("POO", "Outra matéria")

        Assertions.assertEquals(c1, c2)
    }

    @Test
    fun classesWithSameCodeMustHaveSameHashCode() {
        val c1 =
            AcademicClass("POO", "Programação")

        val c2 =
            AcademicClass("POO", "Outra matéria")

        Assertions.assertEquals(
            c1.hashCode(),
            c2.hashCode()
        )
    }
}
