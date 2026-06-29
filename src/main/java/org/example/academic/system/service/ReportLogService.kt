package org.example.academic.system.service

class ReportLogService {
    fun registerLog(
        reportName: String?,
        role: String?
    ) {
        println(
            ("[REPORT LOG] "
                    + reportName
                    + " | Role: "
                    + role)
        )
    }
}