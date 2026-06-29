package org.example.academic.system.service

import org.example.academic.system.exception.AcademicSystemException
import org.example.academic.system.model.AcademicClass
import org.example.academic.system.model.Assessment
import org.example.academic.system.repository.AcademicClassRepository

class AssessmentService(private val classRepository: AcademicClassRepository?) {
    fun registerAssessment(
        academicClass: AcademicClass?,  // ← ADICIONE O ?
        assessment: Assessment?          // ← ADICIONE O ?
    ) {
        if (academicClass == null) {
            throw AcademicSystemException("Turma não encontrada.")
        }

        if (assessment == null) {
            throw AcademicSystemException("Avaliação inválida.")
        }

        academicClass.addAssessment(assessment)
    }
}