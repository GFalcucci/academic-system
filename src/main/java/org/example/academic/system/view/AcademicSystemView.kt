package org.example.academic.system.view

import org.example.academic.system.controller.AcademicSystemController
import org.example.academic.system.controller.AuthenticationController
import org.example.academic.system.exception.AcademicSystemException
import org.example.academic.system.exception.AuthenticationException
import org.example.academic.system.exception.AuthorizationException
import org.example.academic.system.exception.InvalidMenuOptionException
import org.example.academic.system.exception.InvalidNumericInputException
import org.example.academic.system.exception.KeyboardInputException
import org.example.academic.system.model.*
import org.example.academic.system.service.AuthorizationService
import java.lang.SecurityException
import java.lang.System
import java.util.*

class AcademicSystemView(
    private val authController: AuthenticationController,
    private val academicController: AcademicSystemController
) {
    private val scanner: Scanner
    private val authorizationService: AuthorizationService

    init {
        this.scanner = Scanner(System.`in`)
        this.authorizationService = AuthorizationService()
    }

    fun start() {
        while (true) {
            login()

            while (authController.loggedUser != null) {
                try {
                    showMenu()

                    val option = readInt()

                    val user =
                        authController.loggedUser

                    if (user!!.role == Role.ADMIN) {
                        when (option) {
                            1 -> registerClass()
                            2 -> registerAssessment()
                            3 -> generateSummary()
                            4 -> generateWeight()
                            5 -> logout()
                            9 -> {
                                authController.logout()
                                println(
                                    "Sistema encerrado."
                                )
                                System.exit(0)
                            }

                            else -> throw InvalidMenuOptionException(
                                "Opção inválida."
                            )
                        }
                    } else {
                        when (option) {
                            1 -> registerAssessment()
                            2 -> generateSummary()
                            3 -> generateWeight()
                            4 -> logout()
                            9 -> {
                                authController.logout()
                                println(
                                    "Sistema encerrado."
                                )
                                System.exit(0)
                            }

                            else -> throw InvalidMenuOptionException(
                                "Opção inválida."
                            )
                        }
                    }
                } catch (e: KeyboardInputException) {
                    println(
                        "Erro: "
                                + e.message
                    )
                }
            }
        }
    }

    private fun showMenu() {
        val user = authController.loggedUser

        println("\n===== MENU =====")

        if (user!!.role == Role.ADMIN) {
            println("1 - Cadastrar turma")
            println("2 - Registrar avaliação")
            println("3 - Relatório resumido")
            println("4 - Relatório de pesos")
            println("5 - Logout")
            println("9 - Sair do sistema")
        } else {
            println("1 - Registrar avaliação")
            println("2 - Relatório resumido")
            println("3 - Relatório de pesos")
            println("4 - Logout")
            println("9 - Sair do sistema")
        }
    }

    private fun login() {
        var authenticated = false

        while (!authenticated) {
            println("\n===== LOGIN =====")

            print("Usuário: ")
            val username = scanner.nextLine()

            print("Senha: ")
            val password = scanner.nextLine()

            try {
                authenticated = authController.login(
                    username,
                    password
                )
            } catch (e: AuthenticationException) {
                println(
                    e.message
                )
            }
        }

        println(
            "Login realizado com sucesso!"
        )
    }

    fun administrativeOperation() {
        val user = authController.loggedUser

        if (user!!.role != Role.ADMIN) {
            throw SecurityException(
                "Acesso negado."
            )
        }

        // operação administrativa
    }

    private fun registerAssessment() {
        try {
            if (academicController.classes!!.isEmpty()) {
                println(
                    "Nenhuma turma cadastrada."
                )
                return
            }

            println(
                "\nTurmas disponíveis:"
            )

            for (i in academicController.classes!!.indices) {
                val academicClass =
                    academicController.classes!!.get(i)

                println(
                    ((i + 1)
                        .toString() + " - "
                            + academicClass!!.name)
                )
            }

            print(
                "Escolha a turma: "
            )

            val classIndex =
                readInt() - 1

            if (classIndex < 0
                || classIndex >= academicController.classes!!.size
            ) {
                println("Turma inexistente.")
                return
            }

            val selectedClass = academicController.classes!!.get(classIndex)

            println("\nTipo de avaliação:")
            println("1 - Prova")
            println("2 - Trabalho Prático")
            println("3 - Seminário")
            println("4 - Assignment")
            val type = readInt()



            print("Nome: ")
            val name = scanner.nextLine()

            print("Peso: ")
            val weight = readDouble()

            print("Valor: ")
            val value = readDouble()

            var assessment: Assessment? = null

            when (type) {
                1 -> assessment = Exam(name, weight, value)
                2 -> {
                    print("Tecnologia: ")
                    val technology = scanner.nextLine()

                    assessment = PracticalAssignment(name, weight, value, technology)
                }

                3 -> {
                    print("Tema: ")
                    val topic = scanner.nextLine()

                    assessment = Seminar(name, weight, value, topic)
                }

                4 -> assessment = Assignment(
                    name,
                    weight,
                    value
                )

                else -> {
                    println("Tipo inválido.")
                    return
                }
            }

            try {
                academicController.registerAssessment(
                    selectedClass,
                    assessment
                )

                println(
                    "[LOG] Avaliação cadastrada na turma "
                            + selectedClass!!.code
                )

                println(
                    "Avaliação cadastrada com sucesso!"
                )
            } catch (e: AcademicSystemException) {
                println(
                    "Erro: " + e.message
                )
            }
        } catch (e: KeyboardInputException) {
            println(
                "Erro: "
                        + e.message
            )
        } catch (e: AcademicSystemException) {
            println(
                "Erro: "
                        + e.message
            )
        }
    }

    private fun registerClass() {
        try {
            authorizationService.authorize(
                authController.loggedUser,
                Role.ADMIN
            )
        } catch (e: AuthorizationException) {
            println(e.message)
            return
        }

        try {
            print("Código da turma: ")
            val code = scanner.nextLine()

            print("Nome da turma: ")
            val name = scanner.nextLine()

            val academicClass = AcademicClass(code, name)

            academicController.registerClass(
                academicClass
            )

            println(
                "Turma cadastrada com sucesso!"
            )
        } catch (e: AcademicSystemException) {
            println(
                "Erro: " + e.message
            )
        }
    }

    private fun generateSummary() {
        println(
            "[LOG] Relatório resumido gerado por "
                    + authController.loggedUser!!.role
        )

        val report = academicController.generateSummary()

        println(
            "\n===== RELATÓRIO RESUMIDO ====="
        )

        println(report)
    }

    private fun generateWeight() {
        println(
            "[LOG] Relatório de peso gerado por "
                    + authController.loggedUser!!.role
        )

        val report = academicController.generateWeightReport()

        println(
            "\n===== RELATÓRIO DE PESOS ====="
        )

        println(report)
    }

    private fun logout() {
        val user = authController.loggedUser

        println(
            ("[LOG] Usuário "
                    + user!!.username
                    + " realizou logout.")
        )

        authController.logout()

        println(
            "Logout realizado com sucesso."
        )
    }

    @Throws(InvalidNumericInputException::class)
    private fun readInt(): Int {
        try {
            return scanner.nextLine().toInt()
        } catch (e: NumberFormatException) {
            throw InvalidNumericInputException(
                "Digite um número válido."
            )
        }
    }

    @Throws(InvalidNumericInputException::class)
    private fun readDouble(): Double {
        try {
            return scanner.nextLine().toDouble()
        } catch (e: NumberFormatException) {
            throw InvalidNumericInputException(
                "Digite um valor numérico válido."
            )
        }
    }
}