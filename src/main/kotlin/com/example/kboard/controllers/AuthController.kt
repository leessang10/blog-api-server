package com.example.kboard.controllers

import com.example.kboard.dto.ApiResponse
import com.example.kboard.dto.ApiResult
import com.example.kboard.dto.LoginRequest
import com.example.kboard.dto.LoginResponse
import com.example.kboard.infra.jwt.JwtProvider
import com.example.kboard.services.UsersService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val usersService: UsersService,
    private val jwtProvider: JwtProvider
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<ApiResponse<LoginResponse>> {
        println("🔐 로그인 시도: ${request.email}")

        val user = usersService.validateUser(request.email, request.password)
            ?: return ApiResult.error<LoginResponse>("로그인 정보가 잘못되었습니다.", HttpStatus.UNAUTHORIZED)

        val token = jwtProvider.createToken(user.id)
        return ApiResult.ok(LoginResponse(token), "로그인에 성공했습니다.")
    }
}
