package org.example.academic.system.validation

import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.example.academic.system.exception.InvalidAcademicClassException
import org.example.academic.system.model.AcademicClass
import java.util.stream.Collectors

/**
 * TUS-2371 - Validate academic domain objects using Jakarta Bean Validation
 * AC5: Regras declaradas com anotações Jakarta Bean Validation
 * AC6: Lógica centralizada em componente reutilizável
 * AC7: Erros convertidos em exceções de domínio
 * AC8: Separado de Main e da camada de interface
 */
class AcademicClassValidator {
    private val validator: Validator

    init {
        val factory =
            Validation.buildDefaultValidatorFactory()
        this.validator = factory.getValidator()
    }

    fun validate(academicClass: AcademicClass?) {
        val violations =
            validator.validate<AcademicClass?>(academicClass)

        if (!violations.isEmpty()) {
            val messages = violations.stream()
                .map<String?> { obj: ConstraintViolation<AcademicClass?>? -> obj!!.getMessage() }
                .collect(Collectors.joining("; "))

            throw InvalidAcademicClassException(messages)
        }
    }
}