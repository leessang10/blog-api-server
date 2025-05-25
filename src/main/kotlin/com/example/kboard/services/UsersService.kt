package com.example.kboard.services

import com.example.kboard.entities.Users
import com.example.kboard.repositories.UsersRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsersService(
    private val usersRepository: UsersRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun getAllUsers(): List<Users> = usersRepository.findAll()

    fun createUser(user: Users): Users {
        val encodedPassword = passwordEncoder.encode(user.password)
        return usersRepository.save(user.copy(password = encodedPassword))
    }

    fun validateUser(email: String, rawPassword: String): Users? {
        val user = usersRepository.findByEmail(email) ?: return null         // email로 조회
        return if(passwordEncoder.matches(rawPassword, user.password)) user else null   // PW 검증
    }

    fun getUserByEmail(email: String): Users? = usersRepository.findByEmail(email)
}
