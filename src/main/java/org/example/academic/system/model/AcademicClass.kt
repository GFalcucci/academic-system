package org.example.academic.system.model

import jakarta.validation.constraints.NotBlank
import org.example.academic.system.exception.InvalidAcademicClassException

class AcademicClass(

    @field:NotBlank(message = "Código da turma é obrigatório.")
    val code: String,

    @field:NotBlank(message = "Nome da turma é obrigatório.")
    val name: String
) {

    val assessments = mutableListOf<Assessment>()

    init {
        if (code.isBlank()) {
            throw InvalidAcademicClassException("Código da turma é obrigatório.")
        }

        if (name.isBlank()) {
            throw InvalidAcademicClassException("Nome da turma é obrigatório.")
        }
    }

    fun addAssessment(assessment: Assessment) {
        assessments.add(assessment)
    }

    override fun toString(): String {
        return "$code - $name"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AcademicClass) return false

        return code == other.code
    }

    override fun hashCode(): Int {
        return code.hashCode()
    }
}