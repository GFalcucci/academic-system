package org.example.academic.system.controller

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ButtonType
import javafx.stage.Stage
import org.example.academic.system.context.ApplicationContext.Companion.instance
import org.example.academic.system.model.Role
import org.example.academic.system.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class DashboardController {
    @FXML
    lateinit var btnCadastrarTurma: Button

    @FXML
    lateinit var btnRelatorios: Button

    private var usuarioLogado: User? = null
    private var academicController: AcademicSystemController? = null
    private var authController: AuthenticationController? = null

    fun configurarUsuario(user: User?) {
        this.usuarioLogado = user
        logger.info("Dashboard configurando usuário: ${user?.username ?: "null"}")

        if (user?.role != Role.ADMIN) {
            btnCadastrarTurma.isVisible = false
            btnCadastrarTurma.isManaged = false
            logger.debug("Botão de cadastrar turma ocultado para usuário: ${user?.username}")
        } else {
            btnCadastrarTurma.isVisible = true
            btnCadastrarTurma.isManaged = true
            logger.debug("Botão de cadastrar turma visível para ADMIN: ${user.username}")
        }
    }

    fun setAcademicController(academicController: AcademicSystemController?) {
        this.academicController = academicController ?: instance?.academicController.also {
            logger.warn("Dashboard recebeu AcademicController NULL! Recuperando do ApplicationContext...")
        }
        logger.info("Dashboard configurado com AcademicController: ${this.academicController}")
    }

    fun setAuthenticationController(authController: AuthenticationController?) {
        this.authController = authController
        logger.debug("AuthenticationController configurado no Dashboard")
    }

    @FXML
    private fun abrirCadastroTurma() {
        try {
            logger.info("Abrindo cadastro de turma para usuário: ${usuarioLogado?.username ?: "desconhecido"}")

            if (academicController == null) {
                academicController = instance?.academicController
                logger.debug("AcademicController recuperado do ApplicationContext")
            }

            val loader = FXMLLoader(javaClass.getResource("/fxml/class-registration.fxml"))
            val root = loader.load<Parent>()

            val controller = loader.getController<ClassRegistrationController>()
            controller.setAcademicController(academicController)
            controller.configurarUsuario(usuarioLogado)

            val stage = btnCadastrarTurma.scene.window as Stage
            stage.scene = Scene(root)
            stage.title = "Cadastro de Turma"
            stage.show()

            logger.info("Tela de cadastro de turma aberta com sucesso")

        } catch (e: Exception) {
            logger.error("Erro ao abrir cadastro de turma: ${e.message}", e)
            mostrarErro("Erro ao abrir cadastro de turma: ${e.message}")
        }
    }

    @FXML
    private fun abrirVisualizacao() {
        try {
            logger.info("Abrindo visualização de turmas para usuário: ${usuarioLogado?.username ?: "desconhecido"}")

            if (academicController == null) {
                academicController = instance?.academicController
                logger.debug("AcademicController recuperado do ApplicationContext")
            }

            val loader = FXMLLoader(javaClass.getResource("/fxml/viewclasses.fxml"))
            val root = loader.load<Parent>()

            val controller = loader.getController<ViewClassesController>()
            controller.setAcademicController(academicController)
            controller.configurarUsuario(usuarioLogado)

            val stage = btnRelatorios.scene.window as Stage
            stage.scene = Scene(root)
            stage.title = "Visualização de Turmas"
            stage.show()

            logger.info("Tela de visualização de turmas aberta com sucesso")

        } catch (e: Exception) {
            logger.error("Erro ao abrir visualização de turmas: ${e.message}", e)
            mostrarErro("Erro ao abrir visualização de turmas: ${e.message}")
        }
    }

    @FXML
    private fun abrirRelatorios() {
        try {
            val username = usuarioLogado?.username ?: "desconhecido"
            logger.info("Usuário $username abrindo relatórios")

            if (academicController == null) {
                logger.warn("AcademicController é NULL! Recuperando do ApplicationContext...")
                academicController = instance?.academicController
                logger.info("AcademicController recuperado: $academicController")
            }

            val loader = FXMLLoader(javaClass.getResource("/fxml/report.fxml"))
            val root = loader.load<Parent>()

            val controller = loader.getController<ReportController>()
            controller.setAcademicController(academicController)
            controller.configurarUsuario(usuarioLogado)

            logger.info("ReportController configurado com academicController: $academicController")
            logger.info("ReportController configurado com usuário: $username")

            val stage = btnRelatorios.scene.window as Stage
            stage.scene = Scene(root)
            stage.title = "Relatórios"
            stage.show()

            logger.info("Tela de relatórios aberta com sucesso")

        } catch (e: Exception) {
            logger.error("Erro ao abrir tela de relatórios: ${e.message}", e)
            mostrarErro("Erro ao abrir tela de relatórios: ${e.message}")
        }
    }

    @FXML
    private fun abrirCadastroAvaliacao() {
        try {
            val username = usuarioLogado?.username ?: "desconhecido"
            logger.info("Usuário $username abrindo cadastro de avaliação")

            if (academicController == null) {
                academicController = instance?.academicController
                logger.debug("AcademicController recuperado do ApplicationContext")
            }

            val loader = FXMLLoader(javaClass.getResource("/fxml/assessment-registration.fxml"))
            val root = loader.load<Parent>()

            val controller = loader.getController<AssessmentRegistrationController>()
            controller.setAcademicController(academicController)
            controller.setAuthenticationController(authController)
            controller.configurarUsuario(usuarioLogado)

            val stage = btnRelatorios.scene.window as Stage
            stage.scene = Scene(root)
            stage.title = "Cadastro de Avaliação"
            stage.show()

            logger.info("Tela de cadastro de avaliação aberta com sucesso")

        } catch (e: Exception) {
            logger.error("Erro ao abrir cadastro de avaliação: ${e.message}", e)
            mostrarErro("Erro ao abrir cadastro de avaliação: ${e.message}")
        }
    }

    @FXML
    private fun logout() {
        try {
            val username = usuarioLogado?.username ?: "desconhecido"
            logger.info("Usuário $username realizando logout")

            authController?.logout()

            val loader = FXMLLoader(javaClass.getResource("/fxml/login.fxml"))
            val root = loader.load<Parent>()

            val stage = btnRelatorios.scene.window as Stage
            stage.scene = Scene(root)
            stage.title = "Login"
            stage.show()

            logger.info("Logout realizado com sucesso")

        } catch (e: Exception) {
            logger.error("Erro ao realizar logout: ${e.message}", e)
            mostrarErro("Erro ao realizar logout: ${e.message}")
        }
    }

    @FXML
    private fun sairDoSistema() {
        val alert = Alert(Alert.AlertType.CONFIRMATION).apply {
            title = "Sair do Sistema"
            headerText = "Deseja realmente sair do sistema?"
            contentText = "Todas as alterações não salvas serão perdidas."
        }

        val result = alert.showAndWait()

        if (result.isPresent && result.get() == ButtonType.OK) {
            val username = usuarioLogado?.username ?: "desconhecido"

            // Faz logout antes de sair
            authController?.logout()

            logger.info("Sistema encerrado por $username")

            // Fecha completamente a aplicação
            Platform.exit()
            System.exit(0)
        } else {
            logger.debug("Usuário cancelou a operação de sair do sistema")
        }
    }

    private fun mostrarErro(mensagem: String?) {
        logger.error("Erro no Dashboard: $mensagem")

        Alert(Alert.AlertType.ERROR).apply {
            title = "Erro"
            headerText = null
            contentText = mensagem
            showAndWait()
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(DashboardController::class.java)
    }
}