package com.example.kboard.controllers

import com.example.kboard.annotations.CurrentUser
import com.example.kboard.entities.Users
import com.example.kboard.services.UsersService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UsersController(private val usersService: UsersService) {

    @GetMapping("/me")
    fun me(@CurrentUser user: Users): Users {
        return user
    }


    @GetMapping
    fun getAllUsers(): List<Users> = usersService.getAllUsers()

    @PostMapping
    fun createUser(@RequestBody user: Users): Users = usersService.createUser(user)
}
