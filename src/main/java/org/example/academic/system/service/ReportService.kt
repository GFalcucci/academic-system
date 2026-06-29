package org.example.academic.system.service

import org.example.academic.system.model.AcademicClass
import org.example.academic.system.security.SessionManager
import java.io.File
import java.util.Date

class ReportService(
    private val sessionManager: SessionManager?
) {

    private val logService = ReportLogService()

    fun generateSummary(
        classes: MutableList<AcademicClass>
    ): String {

        registerAccess("SUMMARY_REPORT")

        val report = StringBuilder()

        report.append("===== RELATÓRIO RESUMIDO =====\n\n")

        for (c in classes) {

            report.append("Turma: ")
                .append(c.code)
                .append(" - ")
                .append(c.name)
                .append("\n")

            for (a in c.assessments) {
                report.append(a.javaClass.simpleName)
                    .append(" | ")
                    .append(a.name)
                    .append(" | Peso: ")
                    .append(a.weight)
                    .append(" | Valor: ")
                    .append(a.value)
                    .append("\n")
            }

            report.append("\n")
        }

        return report.toString()
    }

    fun generateWeightReport(
        classes: MutableList<AcademicClass>
    ): String {

        registerAccess("WEIGHT_REPORT")

        val report = StringBuilder()

        report.append("===== RELATÓRIO DE PESOS =====\n\n")

        for (c in classes) {

            report.append("Turma: ")
                .append(c.name)
                .append("\n")

            var totalWeight = 0.0

            for (a in c.assessments) {

                report.append(a.name)
                    .append(" - Peso: ")
                    .append(a.weight)
                    .append("\n")

                totalWeight += a.weight
            }

            report.append("Peso total: ")
                .append(totalWeight)
                .append("\n\n")
        }

        return report.toString()
    }

    fun generatePersistenceConfigurationReport(): String {

        registerAccess("PERSISTENCE_CONFIGURATION_REPORT")

        val report = StringBuilder()

        val jsonFile = File("academic_data.json")
        val xmlFile = File("academic_data.xml")

        report.append("===== RELATÓRIO DE PERSISTÊNCIA =====\n\n")
        report.append("Configuração atual: MEMORY\n\n")

        report.append("ARQUIVO JSON\n")
        report.append("Existe: ")
            .append(jsonFile.exists())
            .append("\n")

        if (jsonFile.exists()) {
            report.append("Tamanho: ")
                .append(jsonFile.length())
                .append(" bytes\n")

            report.append("Última modificação: ")
                .append(Date(jsonFile.lastModified()))
                .append("\n")
        }

        report.append("\n")

        report.append("ARQUIVO XML\n")
        report.append("Existe: ")
            .append(xmlFile.exists())
            .append("\n")

        if (xmlFile.exists()) {
            report.append("Tamanho: ")
                .append(xmlFile.length())
                .append(" bytes\n")

            report.append("Última modificação: ")
                .append(Date(xmlFile.lastModified()))
                .append("\n")
        }

        return report.toString()
    }

    private fun registerAccess(reportType: String?) {

        if (sessionManager != null && sessionManager.isAuthenticated) {
            logService.registerLog(
                reportType,
                sessionManager.loggedUser!!.role.toString()
            )
        }
    }
}