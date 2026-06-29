package org.example.academic.system.controller

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.stage.Stage
import org.example.academic.system.context.ApplicationContext.Companion.instance
import org.example.academic.system.model.AcademicClass
import org.example.academic.system.model.Role
import org.example.academic.system.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ClassRegistrationController {
    @FXML
    lateinit var codeField: TextField

    @FXML
    lateinit var nameField: TextField

    @FXML
    lateinit var mensagemLabel: Label

    private var academicController: AcademicSystemController? = null
    private var authController: AuthenticationController? = null
    private var usuarioLogado: User? = null

    @FXML
    fun initialize() {
        logger.info("Inicializando ClassRegistrationController...")
    }

    fun setAcademicController(academicController: AcademicSystemController?) {
        this.academicController = academicController ?: instance?.academicController.also {
            logger.warn("ClassRegistration recebeu AcademicController NULL! Recuperando do ApplicationContext...")
        }
        logger.info("ClassRegistration recuperou AcademicController: {}", this.academicController)
    }

    fun setAuthenticationController(authController: AuthenticationController?) {
        this.authController = authController
        logger.debug("AuthenticationController configurado no ClassRegistrationController")
    }

    fun configurarUsuario(usuario: User?) {
        this.usuarioLogado = usuario
        logger.info(
            "ClassRegistrationController configurando usuário: {}",
            usuario?.username ?: "null"
        )
    }

    @FXML
    private fun cadastrarTurma() {
        try {
            val username = usuarioLogado?.username ?: "desconhecido"
            logger.info("Usuário $username tentando cadastrar turma")

            // Verificação de permissão
            if (usuarioLogado == null || usuarioLogado?.role != Role.ADMIN) {
                logger.warn("Usuário não autorizado tentou cadastrar turma: $username")
                mensagemLabel.text = "Acesso negado. Apenas administradores podem cadastrar turmas."
                mensagemLabel.style = "-fx-text-fill: red;"
                mostrarErro("Acesso negado! Apenas administradores podem cadastrar turmas.")
                return
            }

            // Validação dos campos
            val codigo = codeField.text.trim()
            val nome = nameField.text.trim()

            if (codigo.isEmpty() || nome.isEmpty()) {
                logger.warn("Tentativa de cadastro de turma com campos vazios")
                mensagemLabel.text = "Preencha todos os campos!"
                mensagemLabel.style = "-fx-text-fill: red;"
                return
            }

            // Verifica se o AcademicController está disponível
            if (academicController == null) {
                logger.error("AcademicController não foi inicializado!")
                mensagemLabel.text = "Erro interno: AcademicController não disponível!"
                mensagemLabel.style = "-fx-text-fill: red;"
                return
            }

            // Verifica se o código já existe
            academicController?.findClassByCode(codigo)?.let {
                logger.warn("Tentativa de cadastro com código duplicado: $codigo")
                mensagemLabel.text = "Já existe uma turma com este código: $codigo"
                mensagemLabel.style = "-fx-text-fill: red;"
                return
            }

            // Cria e registra a turma
            val turma = AcademicClass(codigo, nome)
            academicController?.registerClass(turma)

            mensagemLabel.text = "Turma cadastrada com sucesso!"
            mensagemLabel.style = "-fx-text-fill: green;"

            logger.info(
                "Turma '$codigo' - '$nome' cadastrada com sucesso por ${usuarioLogado?.username ?: "desconhecido"}"
            )

            // Limpa os campos após sucesso
            codeField.clear()
            nameField.clear()

        } catch (e: Exception) {
            val username = usuarioLogado?.username ?: "desconhecido"
            logger.error("Erro ao cadastrar turma para usuário $username: ${e.message}", e)
            mensagemLabel.text = "Erro ao cadastrar turma: ${e.message}"
            mensagemLabel.style = "-fx-text-fill: red;"
            mostrarErro("Erro ao cadastrar turma: ${e.message}")
        }
    }

    @FXML
    private fun voltarDashboard() {
        try {
            val username = usuarioLogado?.username ?: "desconhecido"
            logger.info("Usuário $username voltando ao dashboard")

            val loader = FXMLLoader(javaClass.getResource("/fxml/dashboard.fxml"))
            val root = loader.load<Parent>()

            val controller = loader.getController<DashboardController>()
            controller.configurarUsuario(usuarioLogado)
            controller.setAcademicController(academicController)
            controller.setAuthenticationController(authController)

            val stage = codeField.scene.window as Stage
            stage.scene = Scene(root)
            stage.title = "Menu Principal"

            logger.info("Dashboard carregado com sucesso para usuário: $username")

        } catch (e: Exception) {
            logger.error("Erro ao voltar para dashboard: ${e.message}", e)
            mensagemLabel.text = "Erro ao voltar: ${e.message}"
            mensagemLabel.style = "-fx-text-fill: red;"
            mostrarErro("Erro ao voltar para o dashboard: ${e.message}")
        }
    }

    private fun mostrarErro(mensagem: String?) {
        Alert(Alert.AlertType.ERROR).apply {
            title = "Erro"
            headerText = null
            contentText = mensagem
            showAndWait()
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ClassRegistrationController::class.java)
    }
}