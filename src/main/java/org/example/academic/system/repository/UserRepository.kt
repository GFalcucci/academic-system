/* * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template */
package org.example.academic.system.repository

import org.example.academic.system.model.Role
import org.example.academic.system.model.User
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

/**
 * * * @author Gabi Caproni
 */

class UserRepository {
    private val users: MutableList<User> = ArrayList<User>()

    init {
        loadUsers()
    }

    fun findByUsername(username: String?): User? {
        for (user in users) {
            if (user.username == username) {
                return user
            }
        }
        return null
    }

    private fun loadUsers() {
        val file = File("user.txt")
        println("Procurando arquivo em:")
        println(file.getAbsolutePath())
        println("Existe? " + file.exists())
        try {
            BufferedReader(FileReader("user.txt")).use { reader ->
                var line: String?
                while ((reader.readLine().also { line = it }) != null) {
                    val data = line!!.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val username = data[0]
                    val password: String? = data[1]
                    val role = Role.valueOf(data[2])
                    users.add(User(username, password, role))
                }
            }
        } catch (e: IOException) {
            println("Erro ao carregar usuários.")
        }
    }
}
