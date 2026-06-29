package org.example.academic.system.service

import org.example.academic.system.model.AcademicClass
import org.example.academic.system.repository.AcademicClassRepository

class AcademicClassService(
    private val repository: AcademicClassRepository
) {

    fun registerClass(academicClass: AcademicClass?) {
        repository.save(academicClass)
        repository.saveToTxt()
    }

    val classes: MutableList<AcademicClass>
        get() = repository.findAll()

    fun findClassByCode(code: String?): AcademicClass? {
        return repository.findByCode(code)
    }

    fun saveClasses() {
        repository.saveToTxt()
    }
}