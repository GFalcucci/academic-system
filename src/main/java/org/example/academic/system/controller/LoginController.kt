package org.example.academic.system.controller

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.stage.Stage
import org.example.academic.system.context.ApplicationContext.Companion.instance
import org.example.academic.system.exception.AuthenticationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class LoginController {
    @FXML
    lateinit var usernameField: TextField

    @FXML
    lateinit var passwordField: PasswordField

    @FXML
    lateinit var errorLabel: Label

    private var authController: AuthenticationController? = null
    private var academicController: AcademicSystemController? = null

    @FXML
    fun initialize() {
        logger.info("Inicializando LoginController...")

        val context = instance
        authController = context?.authController
        academicController = context?.academicController

        logger.info("LoginController inicializado com sucesso!")
        logger.debug("AcademicController: $academicController")
        logger.debug("AuthenticationController: $authController")

        academicController?.classes?.let {
            logger.info("Total de turmas disponíveis: ${it.size}")
        }
    }

    @FXML
    fun handleLogin() {
        val username = usernameField.text.trim()
        val password = passwordField.text.trim()

        logger.info("Tentativa de login para usuário: $username")

        if (username.isEmpty() || password.isEmpty()) {
            logger.warn("Tentativa de login com campos vazios")
            showError("Preencha todos os campos!")
            return
        }

        try {
            val autenticado = authController?.login(username, password) ?: false

            if (autenticado) {
                val loggedUser = authController?.loggedUser
                logger.info(
                    "Login bem-sucedido para usuário: ${loggedUser?.username} (Role: ${loggedUser?.role})"
                )

                academicController?.classes?.let {
                    logger.info("Total de turmas disponíveis: ${it.size}")
                }

                carregarDashboard()
            }
        } catch (e: AuthenticationException) {
            logger.warn("Falha de autenticação para usuário $username: ${e.message}")
            showError(e.message)
        } catch (e: Exception) {
            logger.error("Erro inesperado durante login do usuário $username: ${e.message}", e)
            showError("Erro inesperado: ${e.message}")
        }
    }

    private fun carregarDashboard() {
        try {
            val loggedUser = authController?.loggedUser
            logger.info("Carregando dashboard para usuário: ${loggedUser?.username}")

            val loader = FXMLLoader(javaClass.getResource("/fxml/dashboard.fxml"))
            val root = loader.load<Parent>()

            val controller = loader.getController<DashboardController>()
            controller.configurarUsuario(loggedUser)
            controller.setAcademicController(academicController)
            controller.setAuthenticationController(authController)

            val stage = usernameField.scene.window as Stage
            stage.scene = Scene(root)
            stage.title = "Academic System"
            stage.show()

            logger.info("Dashboard carregado com sucesso para usuário: ${loggedUser?.username}")

        } catch (e: Exception) {
            logger.error("Erro ao carregar dashboard: ${e.message}", e)
            showError("Erro ao carregar dashboard.")
        }
    }

    private fun showError(message: String?) {
        logger.debug("Exibindo erro: $message")

        errorLabel.text = message
        errorLabel.isVisible = true

        Alert(Alert.AlertType.ERROR).apply {
            title = "Erro"
            headerText = null
            contentText = message
            showAndWait()
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(LoginController::class.java)
    }
}