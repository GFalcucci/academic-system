package org.example.academic.system.repository

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.example.academic.system.model.AcademicData
import java.io.File

class XmlRepository : AcademicRepository {
    private val xmlMapper = XmlMapper()
    private val file = File("academic_data.xml")

    @Throws(Exception::class)
    override fun save(data: AcademicData?) {
        xmlMapper.writeValue(file, data)
    }

    @Throws(Exception::class)
    override fun load(): AcademicData? {
        if (!file.exists()) return AcademicData()
        return xmlMapper.readValue<AcademicData?>(file, AcademicData::class.java)
    }

    override val type: PersistenceType
        get() = PersistenceType.XML
}