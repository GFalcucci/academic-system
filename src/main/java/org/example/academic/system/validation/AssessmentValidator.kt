package org.example.academic.system.validation

import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.example.academic.system.exception.InvalidAssessmentException
import org.example.academic.system.model.Assessment
import java.util.stream.Collectors

/**
 * TUS-2371 - Validate academic domain objects using Jakarta Bean Validation
 * AC5: Regras declaradas com anotações Jakarta Bean Validation
 * AC6: Lógica centralizada em componente reutilizável
 * AC7: Erros convertidos em exceções de domínio
 * AC8: Separado de Main e da camada de interface
 */
class AssessmentValidator {
    private val validator: Validator

    init {
        val factory =
            Validation.buildDefaultValidatorFactory()
        this.validator = factory.getValidator()
    }

    fun validate(assessment: Assessment?) {
        val violations =
            validator.validate<Assessment?>(assessment)

        if (!violations.isEmpty()) {
            val messages = violations.stream()
                .map<String?> { obj: ConstraintViolation<Assessment?>? -> obj!!.getMessage() }
                .collect(Collectors.joining("; "))

            throw InvalidAssessmentException(messages)
        }
    }
}