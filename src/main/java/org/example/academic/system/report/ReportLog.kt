package org.example.academic.system.report

import org.example.academic.system.model.Role
import java.time.LocalDateTime

class ReportLog {
    fun log(
        reportType: String?,
        role: Role?
    ) {
        println(
            ("[LOG] Relatório gerado: "
                    + reportType
                    + " | Perfil: "
                    + role
                    + " | "
                    + LocalDateTime.now())
        )
    }
}