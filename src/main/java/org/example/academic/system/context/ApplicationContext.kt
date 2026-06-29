package org.example.academic.system.context

import org.example.academic.system.controller.AcademicSystemController
import org.example.academic.system.controller.AuthenticationController
import org.example.academic.system.repository.AcademicClassRepository
import org.example.academic.system.repository.UserRepository
import org.example.academic.system.security.SessionManager
import org.example.academic.system.service.AcademicClassService
import org.example.academic.system.service.AssessmentService
import org.example.academic.system.service.ReportService

class ApplicationContext private constructor() {
    private val classRepository: AcademicClassRepository
    val sessionManager: SessionManager
    private val userRepository: UserRepository
    private val classService: AcademicClassService
    private val assessmentService: AssessmentService
    private val reportService: ReportService
    @JvmField
    val academicController: AcademicSystemController
    @JvmField
    val authController: AuthenticationController

    init {
        this.classRepository = AcademicClassRepository()
        this.sessionManager = SessionManager()
        this.userRepository = UserRepository()

        this.classService = AcademicClassService(classRepository)
        this.assessmentService = AssessmentService(classRepository)
        this.reportService = ReportService(sessionManager)

        this.academicController = AcademicSystemController(
            classService,
            assessmentService,
            reportService
        )

        this.authController = AuthenticationController(
            userRepository,
            sessionManager
        )
    }

    companion object {
        @JvmStatic
        var instance: ApplicationContext? = null
            get() {
                if (field == null) {
                    field = ApplicationContext()
                }
                return field
            }
            private set
    }
}