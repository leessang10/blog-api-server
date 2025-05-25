package com.example.kboard.controllers

import com.example.kboard.dto.LoginRequest
import com.example.kboard.dto.LoginResponse
import com.example.kboard.jwt.JwtProvider
import com.example.kboard.services.UsersService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val usersService: UsersService,
    private val jwtProvider: JwtProvider
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<Any> {
        println("🔐 로그인 시도: ${request.email}")

        val user = usersService.validateUser(request.email, request.password)
            ?: return ResponseEntity.status(401).body("Invalid email or password")

        val token = jwtProvider.createToken(user.id)
        return ResponseEntity.ok(LoginResponse(token))
    }
}
