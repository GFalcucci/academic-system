package org.example.academic.system.service

import org.example.academic.system.model.AcademicClass

interface ClassService {

    fun registerClass(
        academicClass: AcademicClass?
    )

    val allClasses: MutableList<AcademicClass>
}