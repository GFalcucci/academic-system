package org.example.academic.system.view

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class AcademicSystemApplication : Application() {

    @Throws(Exception::class)
    override fun start(stage: Stage) {
        val loader = FXMLLoader(javaClass.getResource("/fxml/login.fxml"))
        val root = loader.load<Parent>()

        stage.title = "Academic System"
        stage.scene = Scene(root)
        stage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(AcademicSystemApplication::class.java, *args)
        }
    }
}