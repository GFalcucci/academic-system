package org.example.academic.system.model

import java.io.Serializable

class AcademicData : Serializable {

    val classes = mutableListOf<AcademicClass>()

    val users = mutableListOf<User>()

    companion object {
        private const val serialVersionUID = 1L
    }
}