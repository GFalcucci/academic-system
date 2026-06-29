package org.example.academic.system

import javafx.application.Application
import org.example.academic.system.controller.AcademicSystemController
import org.example.academic.system.controller.AuthenticationController
import org.example.academic.system.model.AcademicClass
import org.example.academic.system.repository.AcademicClassRepository
import org.example.academic.system.repository.UserRepository
import org.example.academic.system.security.SessionManager
import org.example.academic.system.service.AcademicClassService
import org.example.academic.system.service.AssessmentService
import org.example.academic.system.service.ReportService
import org.example.academic.system.view.AcademicSystemApplication

fun main(args: Array<String>) {
    // Repositórios
    val userRepository = UserRepository()
    val classRepository = AcademicClassRepository()

    // Segurança
    val sessionManager = SessionManager()

    // Serviços
    val classService = AcademicClassService(classRepository)
    val assessmentService = AssessmentService(classRepository)
    val reportService = ReportService(sessionManager)

    // Controllers
    val authController = AuthenticationController(userRepository, sessionManager)
    val academicController = AcademicSystemController(classService, assessmentService, reportService)

    // Dados de exemplo (opcional)
    val turma = AcademicClass("POO001", "Programacao")
    academicController.registerClass(turma)
    academicController.saveClasses()

    println("Sistema inicializado com sucesso!")
    println("Arquivo salvo!")

    // Inicia a interface JavaFX
    Application.launch(AcademicSystemApplication::class.java, *args)
}