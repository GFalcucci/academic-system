package org.example.academic.system.repository

import org.example.academic.system.model.AcademicClass
import org.example.academic.system.model.AcademicData
import org.example.academic.system.model.Assessment
import java.io.FileWriter
import java.io.PrintWriter

class TxtRepository : AcademicRepository {
    @Throws(Exception::class)
    override fun save(data: AcademicData?) {
        if (data == null) return

        PrintWriter(FileWriter("assessments.txt")).use { out ->
            for (turma in data.classes) {
                out.println("TURMA;${turma.code};${turma.name}")

                for (av in turma.assessments) {
                    out.println("AVALIACAO;${av.name};${av.weight};${av.value}")
                }
            }
        }
    }

    @Throws(Exception::class)
    override fun load(): AcademicData? {
        // TXT neste caso é Write-Only (apenas exportação de relatório)
        return AcademicData()
    }

    override val type: PersistenceType = PersistenceType.TXT
}