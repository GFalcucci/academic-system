package org.example.academic.system.service

import org.example.academic.system.model.AcademicData
import org.example.academic.system.repository.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PersistenceService {
    private var currentRepository: AcademicRepository? = null
    private val repositories: MutableMap<PersistenceType?, AcademicRepository> =
        HashMap<PersistenceType?, AcademicRepository>()

    init {
        repositories.put(PersistenceType.TXT, TxtRepository())
        repositories.put(PersistenceType.XML, XmlRepository())
        repositories.put(PersistenceType.JSON, JsonRepository())

        setPersistenceType(PersistenceType.JSON)
    }

    fun setPersistenceType(type: PersistenceType?) {
        this.currentRepository = repositories.get(type)
        logger.info("Tipo de persistência alterado para: {}", type)
    }

    fun saveData(data: AcademicData?) {
        try {
            logger.info("Iniciando persistência de dados via {}", currentRepository!!.type)
            currentRepository!!.save(data)
            logger.info("Dados salvos com sucesso.")
        } catch (e: Exception) {
            logger.error("Erro ao salvar dados via {}: {}", currentRepository!!.type, e.message, e)
            throw RuntimeException("Falha na persistência", e)
        }
    }

    fun loadData(): AcademicData {
        try {
            logger.info("Carregando dados via {}", currentRepository!!.type)

            val data = currentRepository!!.load()

            logger.info("Dados carregados com sucesso.")

            return data ?: AcademicData()
        } catch (e: Exception) {
            logger.error(
                "Erro ao carregar dados via {}: {}",
                currentRepository!!.type,
                e.message,
                e
            )
            throw RuntimeException("Falha ao carregar dados", e)
        }
    }

    fun generateConfigurationReport(): String {
        val report = "=== Relatório de Configuração ===\n" +
                "Tipo Atual: " + currentRepository!!.type + "\n" +
                "Tipos Suportados: TXT, XML, JSON\n" +
                "Status: Operante\n" +
                "================================="
        logger.debug("Relatório de configuração gerado.")
        return report
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(PersistenceService::class.java)
    }
}