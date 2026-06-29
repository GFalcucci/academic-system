package org.example.academic.system.repository

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.academic.system.model.AcademicData
import java.io.File

class JsonRepository : AcademicRepository {
    private val mapper = ObjectMapper()
    private val file = File("academic_data.json")

    @Throws(Exception::class)
    override fun save(data: AcademicData?) {
        mapper.writeValue(file, data)
    }

    @Throws(Exception::class)
    override fun load(): AcademicData? {
        if (!file.exists()) return AcademicData()
        return mapper.readValue(file, AcademicData::class.java)
    }

    // CORRIGIDO: Adicionado 'override'
    override val type: PersistenceType
        get() = PersistenceType.JSON
}