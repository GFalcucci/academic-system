package org.example.academic.system.report

import org.example.academic.system.model.AcademicClass
import org.example.academic.system.model.Role

class SummaryAssessmentReport {

    private val log = ReportLog()

    fun generate(
        academicClass: AcademicClass,
        role: Role?
    ): String {

        log.log(
            "SUMMARY_REPORT",
            role
        )

        return """
    Turma: ${academicClass.name}
    Total de avaliações: ${academicClass.assessments.size}
""".trimIndent()
    }
}