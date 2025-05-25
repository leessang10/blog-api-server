package com.example.kboard.controllers

import com.example.kboard.entities.Users
import com.example.kboard.services.UsersService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UsersController(private val usersService: UsersService) {

    @GetMapping
    fun getAllUsers(): List<Users> = usersService.getAllUsers()

    @PostMapping
    fun createUser(@RequestBody user: Users): Users = usersService.createUser(user)
}
