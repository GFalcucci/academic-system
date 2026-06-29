package org.example.academic.system.controller

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.TextArea
import javafx.stage.Stage
import org.example.academic.system.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ViewClassesController {
    @FXML
    lateinit var resultadoArea: TextArea

    private var academicController: AcademicSystemController? = null
    private var usuarioLogado: User? = null

    fun setAcademicController(academicController: AcademicSystemController?) {
        this.academicController = academicController
        logger.debug("ViewClassesController recebeu AcademicController: $academicController")
    }

    fun configurarUsuario(user: User?) {
        this.usuarioLogado = user
        logger.info("ViewClassesController configurando usuário: ${user?.username ?: "null"}")
    }

    @FXML
    private fun carregarTurmas() {
        try {
            val username = usuarioLogado?.username ?: "desconhecido"
            logger.info("Carregando turmas para visualização. Usuário: $username")

            val controller = academicController
            if (controller == null) {
                logger.error("AcademicController não foi inicializado!")
                resultadoArea.text = "Erro: AcademicController não foi inicializado!"
                return
            }

            val classes = controller.classes
            logger.info("Total de turmas a serem exibidas: ${classes.size}")

            val texto = StringBuilder()

            if (classes.isEmpty()) {
                texto.append("Nenhuma turma cadastrada no sistema.\n")
                texto.append("Cadastre uma turma para visualizar as avaliações.")
            } else {
                for (turma in classes) {
                    texto.append("Turma: ${turma.code} - ${turma.name}\n\n")

                    val assessments = turma.assessments
                    if (assessments.isEmpty()) {
                        texto.append("  Nenhuma avaliação cadastrada.\n\n")
                    } else {
                        for (avaliacao in assessments) {
                            texto.append("  Tipo: ${avaliacao.javaClass.simpleName}\n")
                            texto.append("  Nome: ${avaliacao.name}\n")
                            texto.append("  Peso: ${avaliacao.weight}\n")
                            texto.append("  Valor: ${avaliacao.value}\n\n")
                        }
                    }
                    texto.append("---------------------------------\n")
                }
            }

            resultadoArea.text = texto.toString()
            logger.info("Visualização de turmas carregada com sucesso")

        } catch (e: Exception) {
            logger.error("Erro ao carregar turmas: ${e.message}", e)
            resultadoArea.text = "Erro ao carregar turmas: ${e.message}"
        }
    }

    @FXML
    private fun voltarDashboard() {
        try {
            val username = usuarioLogado?.username ?: "desconhecido"
            logger.info("Voltando ao dashboard. Usuário: $username")

            val loader = FXMLLoader(javaClass.getResource("/fxml/dashboard.fxml"))
            val root = loader.load<Parent>()

            val controller = loader.getController<DashboardController>()
            controller.configurarUsuario(usuarioLogado)
            controller.setAcademicController(academicController)

            val stage = resultadoArea.scene.window as Stage
            stage.scene = Scene(root)
            stage.title = "Menu Principal"

            logger.info("Dashboard carregado com sucesso")

        } catch (e: Exception) {
            logger.error("Erro ao voltar para dashboard: ${e.message}", e)
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ViewClassesController::class.java)
    }
}