package org.example.academic.system.report

import org.example.academic.system.model.AcademicClass
import org.example.academic.system.model.Role

class AssessmentWeightReport {
    private val log = ReportLog()

    fun generate(academicClass: AcademicClass, role: Role?): String {
        log.log("WEIGHT_REPORT", role)

        val sb = StringBuilder()
        var totalWeight = 0.0

        for (assessment in academicClass.assessments) {
            sb.append("${assessment.name} - ${assessment.weight}\n")
            totalWeight += assessment.weight
        }

        sb.append("\nPeso total: $totalWeight")

        return sb.toString()
    }
}