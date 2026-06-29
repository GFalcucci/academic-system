import org.example.academic.system.controller.AcademicSystemController
import org.example.academic.system.model.*
import org.example.academic.system.repository.AcademicClassRepository
import org.example.academic.system.security.SessionManager
import org.example.academic.system.service.AcademicClassService
import org.example.academic.system.service.AssessmentService
import org.example.academic.system.service.ReportService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AcademicSystemControllerTest {
    private fun createReportService(): ReportService {
        val sessionManager =
            SessionManager()

        val user =
            User(
                "admin",
                "123",
                Role.ADMIN
            )

        sessionManager.login(user)

        return ReportService(
            sessionManager
        )
    }

    @Test
    fun shouldRegisterClass() {
        val repository =
            AcademicClassRepository()

        val classService =
            AcademicClassService(
                repository
            )

        val assessmentService =
            AssessmentService(
                repository
            )

        val reportService =
            createReportService()

        val controller =
            AcademicSystemController(
                classService,
                assessmentService,
                reportService
            )

        val academicClass =
            AcademicClass(
                "POO",
                "Programacao Orientada a Objetos"
            )

        controller.registerClass(
            academicClass
        )

        Assertions.assertEquals(
            1,
            controller.classes!!.size
        )
    }

    @Test
    fun shouldRegisterAssessment() {
        val repository =
            AcademicClassRepository()

        val classService =
            AcademicClassService(
                repository
            )

        val assessmentService =
            AssessmentService(
                repository
            )

        val reportService =
            createReportService()

        val controller =
            AcademicSystemController(
                classService,
                assessmentService,
                reportService
            )

        val academicClass =
            AcademicClass(
                "POO",
                "Programacao"
            )

        controller.registerClass(
            academicClass
        )

        val assessment: Assessment =
            Exam(
                "P1",
                10.0,
                100.0
            )

        controller.registerAssessment(
            academicClass,
            assessment
        )

        Assertions.assertEquals(
            1,
            academicClass.assessments.size
        )
    }

    @Test
    fun shouldGenerateSummaryReport() {
        val repository =
            AcademicClassRepository()

        val classService =
            AcademicClassService(
                repository
            )

        val assessmentService =
            AssessmentService(
                repository
            )

        val reportService =
            createReportService()

        val controller =
            AcademicSystemController(
                classService,
                assessmentService,
                reportService
            )

        val report =
            controller.generateSummary()

        Assertions.assertNotNull(report)
    }

    @Test
    fun shouldGenerateWeightReport() {
        val repository =
            AcademicClassRepository()

        val classService =
            AcademicClassService(
                repository
            )

        val assessmentService =
            AssessmentService(
                repository
            )

        val reportService =
            createReportService()

        val controller =
            AcademicSystemController(
                classService,
                assessmentService,
                reportService
            )

        val report =
            controller.generateWeightReport()

        Assertions.assertNotNull(report)
    }
}