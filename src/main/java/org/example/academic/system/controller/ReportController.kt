package org.example.academic.system.controller

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.stage.Stage
import org.example.academic.system.context.ApplicationContext.Companion.instance
import org.example.academic.system.model.Role
import org.example.academic.system.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ReportController {
    @FXML
    lateinit var btnPersistencia: Button

    @FXML
    lateinit var resultadoArea: TextArea

    private var academicController: AcademicSystemController? = null
    private var usuarioLogado: User? = null

    fun setAcademicController(academicController: AcademicSystemController?) {
        this.academicController = academicController ?: instance?.academicController.also {
            if (academicController == null) {
                logger.warn("ReportController recebeu AcademicController NULL! Recuperando do ApplicationContext...")
            }
        }

        logger.info("ReportController configurado com AcademicController: ${this.academicController}")

        this.academicController?.classes?.let {
            logger.info("Total de turmas disponíveis no ReportController: ${it.size}")
        }
    }

    fun configurarUsuario(user: User?) {
        this.usuarioLogado = user
        logger.info("ReportController configurando usuário: ${user?.username ?: "null"}")

        user?.let {
            if (it.role == Role.ADMIN) {
                btnPersistencia.isVisible = true
                btnPersistencia.isManaged = true
                logger.debug("Botão de persistência visível para ADMIN: ${it.username}")
            } else {
                btnPersistencia.isVisible = false
                btnPersistencia.isManaged = false
                logger.debug("Botão de persistência oculto para usuário: ${it.username}")
            }
        }
    }

    @FXML
    private fun gerarResumo() {
        try {
            val username = usuarioLogado?.username ?: "desconhecido"
            logger.info("Gerando relatório resumido para usuário: $username")

            if (academicController == null) {
                academicController = instance?.academicController
            }

            val controller = academicController
            if (controller == null) {
                logger.error("AcademicController não foi inicializado!")
                resultadoArea.text = "Erro: AcademicController não foi inicializado!"
                mostrarErro("AcademicController não foi inicializado!")
                return
            }

            val relatorio = controller.generateSummary()
            logger.info("Relatório resumido gerado com sucesso. Tamanho: ${relatorio.length} caracteres")

            resultadoArea.text = relatorio

        } catch (e: Exception) {
            logger.error("Erro ao gerar relatório resumido: ${e.message}", e)
            resultadoArea.text = "Erro ao gerar relatório: ${e.message}"
            mostrarErro("Erro ao gerar relatório resumido: ${e.message}")
        }
    }

    @FXML
    private fun gerarPeso() {
        try {
            val username = usuarioLogado?.username ?: "desconhecido"
            logger.info("Gerando relatório de pesos para usuário: $username")

            if (academicController == null) {
                academicController = instance?.academicController
            }

            val controller = academicController
            if (controller == null) {
                logger.error("AcademicController não foi inicializado!")
                resultadoArea.text = "Erro: AcademicController não foi inicializado!"
                mostrarErro("AcademicController não foi inicializado!")
                return
            }

            val relatorio = controller.generateWeightReport()
            logger.info("Relatório de pesos gerado com sucesso. Tamanho: ${relatorio.length} caracteres")

            resultadoArea.text = relatorio

        } catch (e: Exception) {
            logger.error("Erro ao gerar relatório de pesos: ${e.message}", e)
            resultadoArea.text = "Erro ao gerar relatório: ${e.message}"
            mostrarErro("Erro ao gerar relatório de peso: ${e.message}")
        }
    }

    @FXML
    private fun gerarPersistencia() {
        try {
            val username = usuarioLogado?.username ?: "desconhecido"
            logger.info("Gerando relatório de persistência para usuário: $username")

            if (academicController == null) {
                academicController = instance?.academicController
            }

            val controller = academicController
            if (controller == null) {
                logger.error("AcademicController não foi inicializado!")
                resultadoArea.text = "Erro: AcademicController não foi inicializado!"
                mostrarErro("AcademicController não foi inicializado!")
                return
            }

            if (usuarioLogado?.role != Role.ADMIN) {
                logger.warn("Tentativa de acesso ao relatório de persistência por usuário não autorizado: $username")
                resultadoArea.text = "Acesso negado! Apenas administradores podem acessar este relatório."
                mostrarErro("Acesso negado! Apenas administradores podem acessar este relatório.")
                return
            }

            val relatorio = controller.generatePersistenceConfigurationReport()
            logger.info("Relatório de persistência gerado com sucesso. Tamanho: ${relatorio.length} caracteres")

            resultadoArea.text = relatorio

        } catch (e: Exception) {
            logger.error("Erro ao gerar relatório de persistência: ${e.message}", e)
            resultadoArea.text = "Erro ao gerar relatório: ${e.message}"
            mostrarErro("Erro ao gerar relatório de persistência: ${e.message}")
        }
    }

    @FXML
    private fun voltar() {
        try {
            val username = usuarioLogado?.username ?: "desconhecido"
            logger.info("Usuário $username voltando ao dashboard")

            val loader = FXMLLoader(javaClass.getResource("/fxml/dashboard.fxml"))
            val root = loader.load<Parent>()

            val controller = loader.getController<DashboardController>()
            controller.configurarUsuario(usuarioLogado)
            controller.setAcademicController(academicController)
            controller.setAuthenticationController(null)

            val stage = resultadoArea.scene.window as Stage
            stage.scene = Scene(root)
            stage.title = "Menu Principal"
            stage.show()

            logger.info("Voltou ao dashboard com sucesso")

        } catch (e: Exception) {
            logger.error("Erro ao voltar: ${e.message}", e)
            mostrarErro("Erro ao voltar: ${e.message}")
        }
    }

    private fun mostrarErro(mensagem: String?) {
        logger.error("Erro no ReportController: $mensagem")

        Alert(AlertType.ERROR).apply {
            title = "Erro"
            headerText = null
            contentText = mensagem
            showAndWait()
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ReportController::class.java)
    }
}