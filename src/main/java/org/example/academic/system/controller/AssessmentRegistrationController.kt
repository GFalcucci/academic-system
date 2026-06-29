package org.example.academic.system.controller

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.stage.Stage
import org.example.academic.system.context.ApplicationContext
import org.example.academic.system.model.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class AssessmentRegistrationController {
    @FXML
    lateinit var classCombo: ComboBox<AcademicClass>

    @FXML
    lateinit var typeCombo: ComboBox<String>

    @FXML
    lateinit var nameField: TextField

    @FXML
    lateinit var weightField: TextField

    @FXML
    lateinit var valueField: TextField

    @FXML
    lateinit var campoExtraLabel: Label

    @FXML
    lateinit var campoExtraField: TextField

    @FXML
    lateinit var mensagemLabel: Label

    private var academicController: AcademicSystemController? = null
    private var authController: AuthenticationController? = null
    private var usuarioLogado: User? = null

    @FXML
    fun initialize() {
        carregarTurmas()
        carregarTiposAvaliacao()
        typeCombo.setOnAction { atualizarFormulario() }
    }

    fun setAcademicController(academicController: AcademicSystemController?) {
        this.academicController = academicController ?: ApplicationContext.instance?.academicController
        carregarTurmas()
    }

    fun setAuthenticationController(authController: AuthenticationController?) {
        this.authController = authController
    }

    fun configurarUsuario(usuario: User?) {
        this.usuarioLogado = usuario
    }

    private fun carregarTurmas() {
        try {
            val controller = academicController ?: return
            classCombo.items.clear()
            controller.classes?.let { classCombo.items.addAll(it) }
        } catch (e: Exception) {
            logger.error("Erro ao carregar turmas", e)
        }
    }

    private fun carregarTiposAvaliacao() {
        typeCombo.items.clear()
        typeCombo.items.addAll("PROVA", "SEMINARIO", "TRABALHO PRATICO")
    }

    private fun atualizarFormulario() {
        val tipo = typeCombo.value
        campoExtraField.clear()

        if (tipo == null) {
            campoExtraLabel.isVisible = false
            campoExtraField.isVisible = false
            campoExtraField.isManaged = false
            return
        }

        when (tipo) {
            "SEMINARIO" -> {
                campoExtraLabel.text = "Tema"
                campoExtraLabel.isVisible = true
                campoExtraField.promptText = "Informe o tema do seminário"
                campoExtraField.isVisible = true
                campoExtraField.isManaged = true
            }
            "TRABALHO PRATICO" -> {
                campoExtraLabel.text = "Tecnologia"
                campoExtraLabel.isVisible = true
                campoExtraField.promptText = "Ex: Java, Python, React"
                campoExtraField.isVisible = true
                campoExtraField.isManaged = true
            }
            else -> {
                campoExtraLabel.isVisible = false
                campoExtraField.isVisible = false
                campoExtraField.isManaged = false
            }
        }
    }

    @FXML
    private fun cadastrarAvaliacao() {
        try {
            val turma = classCombo.value
            if (turma == null) {
                mensagemLabel.text = "Selecione uma turma!"
                return
            }

            val tipo = typeCombo.value
            if (tipo == null) {
                mensagemLabel.text = "Selecione um tipo!"
                return
            }

            val nome = nameField.text.trim()
            val peso = weightField.text.toDouble()
            val valor = valueField.text.toDouble()

            val assessment: Assessment = when (tipo) {
                "PROVA" -> Exam(nome, peso, valor)
                "SEMINARIO" -> {
                    val tema = campoExtraField.text.trim()
                    if (tema.isEmpty()) {
                        mensagemLabel.text = "Informe o tema!"
                        return
                    }
                    Seminar(nome, peso, valor, tema)
                }
                "TRABALHO PRATICO" -> {
                    val tecnologia = campoExtraField.text.trim()
                    if (tecnologia.isEmpty()) {
                        mensagemLabel.text = "Informe a tecnologia!"
                        return
                    }
                    PracticalAssignment(nome, peso, valor, tecnologia)
                }
                else -> throw IllegalArgumentException("Tipo inválido")
            }

            academicController?.registerAssessment(turma, assessment)

            mensagemLabel.text = "Avaliação cadastrada com sucesso!"
            limparCampos()

        } catch (e: NumberFormatException) {
            mensagemLabel.text = "Peso e valor devem ser numéricos!"
        } catch (e: Exception) {
            mensagemLabel.text = "Erro: ${e.message}"
            logger.error("Erro ao cadastrar avaliação", e)
        }
    }

    private fun limparCampos() {
        nameField.clear()
        weightField.clear()
        valueField.clear()
        campoExtraField.clear()
        classCombo.selectionModel.clearSelection()
        typeCombo.selectionModel.clearSelection()
        campoExtraField.isVisible = false
        campoExtraField.isManaged = false
        campoExtraLabel.isVisible = false
    }

    @FXML
    private fun voltarDashboard() {
        try {
            val loader = FXMLLoader(javaClass.getResource("/fxml/dashboard.fxml"))
            val root = loader.load<Parent>()
            val controller = loader.getController<DashboardController>()

            controller.configurarUsuario(usuarioLogado)
            controller.setAcademicController(academicController)
            controller.setAuthenticationController(authController)

            val stage = classCombo.scene.window as Stage
            stage.scene = Scene(root)
            stage.title = "Menu Principal"

        } catch (e: Exception) {
            logger.error("Erro ao voltar", e)
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AssessmentRegistrationController::class.java)
    }
}