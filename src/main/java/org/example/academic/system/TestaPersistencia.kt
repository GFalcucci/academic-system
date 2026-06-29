package org.example.academic.system

import org.example.academic.system.model.AcademicClass
import org.example.academic.system.model.AcademicData
import org.example.academic.system.repository.PersistenceType
import org.example.academic.system.service.PersistenceService

object TestaPersistencia {

    @JvmStatic
    fun main(args: Array<String>) {

        val service = PersistenceService()

        println("--- 1. CRIANDO DADOS DE TESTE ---")

        val data = AcademicData()

        val turma = AcademicClass(
            "P001",
            "Programacao Avancada"
        )

        data.classes.add(turma)

        // TESTANDO JSON
        println("\n--- 2. TESTANDO JSON (SALVAR E CARREGAR) ---")

        service.setPersistenceType(PersistenceType.JSON)
        service.saveData(data)

        val dadosJson = service.loadData()

        println(
            "JSON carregado com sucesso! Turmas encontradas: ${
                dadosJson.classes.size
            }"
        )

        // TESTANDO XML
        println("\n--- 3. TESTANDO XML (SALVAR E CARREGAR) ---")

        service.setPersistenceType(PersistenceType.XML)
        service.saveData(data)

        val dadosXml = service.loadData()

        println(
            "XML carregado com sucesso! Turmas encontradas: ${
                dadosXml.classes.size
            }"
        )

        println("\n--- TESTE FINALIZADO COM SUCESSO! ---")
    }
}