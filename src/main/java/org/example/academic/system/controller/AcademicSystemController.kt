package org.example.academic.system.controller

import org.example.academic.system.model.AcademicClass
import org.example.academic.system.model.Assessment
import org.example.academic.system.service.AcademicClassService
import org.example.academic.system.service.AssessmentService
import org.example.academic.system.service.ReportService

class AcademicSystemController(
    private val classService: AcademicClassService,
    private val assessmentService: AssessmentService,
    private val reportService: ReportService
) {

    fun registerClass(academicClass: AcademicClass?) {
        classService.registerClass(academicClass)
    }

    fun registerAssessment(academicClass: AcademicClass?, assessment: Assessment?) {
        assessmentService.registerAssessment(academicClass, assessment)
    }

    // CORRIGIDO: Remove o '?' para corresponder ao tipo retornado
    val classes: MutableList<AcademicClass>
        get() = classService.classes

    fun findClassByCode(code: String?): AcademicClass? {
        return classService.findClassByCode(code)
    }

    fun saveClasses() {
        classService.saveClasses()
    }

    fun generateSummary(): String {
        return reportService.generateSummary(classService.classes)
    }

    fun generateWeightReport(): String {
        return reportService.generateWeightReport(classService.classes)
    }

    fun generatePersistenceConfigurationReport(): String {
        return reportService.generatePersistenceConfigurationReport()
    }
}