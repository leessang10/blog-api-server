package com.example.kboard.services

import com.example.kboard.entities.Users
import com.example.kboard.repositories.UsersRepository
import org.springframework.stereotype.Service

@Service
class UsersService(private val usersRepository: UsersRepository) {

    fun getAllUsers(): List<Users> = usersRepository.findAll()

    fun createUser(user: Users): Users = usersRepository.save(user)

    fun getUserByEmail(email: String): Users? = usersRepository.findByEmail(email)
}
