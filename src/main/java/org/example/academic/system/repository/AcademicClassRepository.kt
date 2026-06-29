package org.example.academic.system.repository

import org.example.academic.system.model.AcademicClass
import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter

class AcademicClassRepository {
    private val classes: MutableList<AcademicClass> = ArrayList<AcademicClass>()

    fun save(
        academicClass: AcademicClass?
    ) {
        classes.add(
            academicClass!!
        )
    }

    fun findAll(): MutableList<AcademicClass> {
        return classes
    }

    fun findByCode(
        code: String?
    ): AcademicClass? {
        for (academicClass in classes) {
            if (academicClass.code
                == code
            ) {
                return academicClass
            }
        }

        return null
    }

    fun saveToTxt() {
        try {
            PrintWriter(
                FileWriter(
                    "classes.txt"
                )
            ).use { writer ->
                for (academicClass
                in classes) {
                    writer.println(
                        (academicClass.code
                                + ";"
                                + academicClass.name)
                    )
                }
            }
        } catch (e: IOException) {
            throw RuntimeException(
                "Erro ao salvar arquivo.",
                e
            )
        }
    }
}