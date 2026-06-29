package org.example.academic.system.controller

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.stage.Stage
import org.example.academic.system.model.Role
import org.example.academic.system.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PersistenceConfigController {
    @FXML
    lateinit var infoArea: TextArea

    @FXML
    lateinit var statusLabel: Label

    private var usuarioLogado: User? = null
    private var academicController: AcademicSystemController? = null

    fun setAcademicController(academicController: AcademicSystemController?) {
        this.academicController = academicController
        logger.debug("PersistenceConfigController recebeu AcademicController: $academicController")
    }

    fun configurarUsuario(user: User?) {
        this.usuarioLogado = user
        logger.info(
            "PersistenceConfigController configurando usuário: ${user?.username ?: "null"}"
        )

        if (user?.role != Role.ADMIN) {
            logger.warn(
                "Usuário não autorizado tentou acessar configuração de persistência: ${user?.username ?: "null"}"
            )
            statusLabel.text = "Acesso negado! Apenas administradores."
            statusLabel.style = "-fx-text-fill: red;"
        } else {
            carregarInformacoes()
        }
    }

    @FXML
    private fun initialize() {
        logger.info("Inicializando PersistenceConfigController...")
    }

    @FXML
    private fun handleRefresh() {
        logger.info(
            "Usuário ${usuarioLogado?.username ?: "desconhecido"} atualizando informações de persistência"
        )
        carregarInformacoes()
        statusLabel.text = "✅ Informações atualizadas!"
        statusLabel.style = "-fx-text-fill: green;"
    }

    @FXML
    private fun handleReload() {
        try {
            logger.info(
                "Usuário ${usuarioLogado?.username ?: "desconhecido"} recarregando dados"
            )

            academicController?.let {
                it.saveClasses()
                statusLabel.text = "✅ Dados recarregados com sucesso!"
                statusLabel.style = "-fx-text-fill: green;"
                carregarInformacoes()
                logger.info("Dados recarregados com sucesso")
            } ?: run {
                statusLabel.text = "❌ AcademicController não disponível!"
                statusLabel.style = "-fx-text-fill: red;"
                logger.error("AcademicController não disponível para recarregar dados")
            }

        } catch (e: Exception) {
            logger.error("Erro ao recarregar dados: ${e.message}", e)
            statusLabel.text = "❌ Erro ao recarregar: ${e.message}"
            statusLabel.style = "-fx-text-fill: red;"
        }
    }

    @FXML
    private fun handleVoltar() {
        try {
            logger.info(
                "Usuário ${usuarioLogado?.username ?: "desconhecido"} voltando ao dashboard"
            )

            val loader = FXMLLoader(javaClass.getResource("/fxml/dashboard.fxml"))
            val root = loader.load<Parent>()

            val controller = loader.getController<DashboardController>()
            controller.configurarUsuario(usuarioLogado)
            controller.setAcademicController(academicController)

            val stage = statusLabel.scene.window as Stage
            stage.scene = Scene(root)
            stage.title = "Menu Principal"

            logger.info("Dashboard carregado com sucesso")

        } catch (e: Exception) {
            logger.error("Erro ao voltar: ${e.message}", e)
            mostrarErro("Erro ao voltar: ${e.message}")
        }
    }

    private fun carregarInformacoes() {
        try {
            val sb = StringBuilder()
            sb.append("=== CONFIGURAÇÃO DE PERSISTÊNCIA ===\n\n")

            val userFile = File("users.txt")
            sb.append("📁 ARQUIVO DE USUÁRIOS:\n")
            sb.append("   Local: ${userFile.absolutePath}\n")
            sb.append("   Existe: ${if (userFile.exists()) "✅ SIM" else "❌ NÃO"}\n")

            if (userFile.exists()) {
                sb.append("   Tamanho: ${userFile.length()} bytes\n")
                sb.append("   Última modificação: ${
                    SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                        .format(Date(userFile.lastModified()))
                }\n")
            }
            sb.append("\n")

            sb.append("💾 INFORMAÇÕES DO SISTEMA:\n")
            sb.append("   Total de turmas: ${academicController?.classes?.size ?: 0}\n")
            sb.append("   Usuário atual: ${usuarioLogado?.username ?: "N/A"}\n")
            sb.append("   Role: ${usuarioLogado?.role ?: "N/A"}\n")

            infoArea.text = sb.toString()
            logger.debug("Informações de persistência carregadas com sucesso")

        } catch (e: Exception) {
            logger.error("Erro ao carregar informações de persistência: ${e.message}", e)
            infoArea.text = "Erro ao carregar informações: ${e.message}"
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
        private val logger: Logger = LoggerFactory.getLogger(PersistenceConfigController::class.java)
    }
}