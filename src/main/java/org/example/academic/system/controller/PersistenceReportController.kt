package org.example.academic.system.controller

import javafx.fxml.FXML
import javafx.scene.control.TextArea
import java.nio.file.Files
import java.nio.file.Path

class PersistenceReportController {
    @FXML
    private val conteudoArea: TextArea? = null

    @FXML
    private fun carregarJson() {
        try {
            val conteudo =
                Files.readString(
                    Path.of("academic_data.json")
                )

            conteudoArea!!.setText(conteudo)
        } catch (e: Exception) {
            conteudoArea!!.setText(
                "Erro ao carregar JSON:\n"
                        + e.message
            )
        }
    }

    @FXML
    private fun carregarXml() {
        try {
            val conteudo =
                Files.readString(
                    Path.of("academic_data.xml")
                )

            conteudoArea!!.setText(conteudo)
        } catch (e: Exception) {
            conteudoArea!!.setText(
                "Erro ao carregar XML:\n"
                        + e.message
            )
        }
    }

    @FXML
    private fun voltar() {
        // código para retornar ao dashboard
    }
}