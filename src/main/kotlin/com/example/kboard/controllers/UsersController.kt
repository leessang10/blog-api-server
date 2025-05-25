package com.example.kboard.controllers

import com.example.kboard.annotations.CurrentUser
import com.example.kboard.dto.ApiResponse
import com.example.kboard.dto.ApiResult
import com.example.kboard.entities.Users
import com.example.kboard.services.UsersService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UsersController(private val usersService: UsersService) {

    @GetMapping("/me")
    fun me(@CurrentUser user: Users): ResponseEntity<ApiResponse<Users>> {
        return ApiResult.ok(user)
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<ApiResponse<List<Users>>> {
        val users = usersService.getAllUsers()
        return ApiResult.ok(users)
    }

    @PostMapping
    fun createUser(@RequestBody user: Users): ResponseEntity<ApiResponse<Users>> {
        val created = usersService.createUser(user)
        return ApiResult.ok(created)
    }
}
