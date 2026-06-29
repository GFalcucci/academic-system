package org.example.academic.system.service

import org.example.academic.system.model.AcademicClass
import org.example.academic.system.repository.AcademicClassRepository

class ClassServiceImpl(
    private val repository: AcademicClassRepository
) : ClassService {

    override fun registerClass(academicClass: AcademicClass?) {
        repository.save(academicClass)
    }

    override val allClasses: MutableList<AcademicClass>
        get() = repository.findAll()
}