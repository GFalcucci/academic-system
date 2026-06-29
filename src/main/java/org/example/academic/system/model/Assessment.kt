package org.example.academic.system.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import org.example.academic.system.exception.InvalidAssessmentException

abstract class Assessment(

    @field:NotBlank(message = "Nome da avaliação é obrigatório.")
    val name: String,

    @field:Positive(message = "Peso deve ser maior que zero.")
    val weight: Double,

    @field:Positive(message = "Valor deve ser maior que zero.")
    val value: Double
) {

    init {
        if (name.isBlank()) {
            throw InvalidAssessmentException(
                "Nome da avaliação é obrigatório."
            )
        }

        if (weight <= 0) {
            throw InvalidAssessmentException(
                "Peso deve ser maior que zero."
            )
        }

        if (value <= 0) {
            throw InvalidAssessmentException(
                "Valor deve ser maior que zero."
            )
        }
    }
}