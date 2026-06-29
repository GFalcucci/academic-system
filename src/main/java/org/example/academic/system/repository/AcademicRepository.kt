package org.example.academic.system.repository

import org.example.academic.system.model.AcademicData

interface AcademicRepository {

    @Throws(Exception::class)
    fun save(data: AcademicData?)

    @Throws(Exception::class)
    fun load(): AcademicData?

    val type: PersistenceType
}