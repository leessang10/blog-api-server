package com.example.kboard.dto

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object ApiResult {

    fun <T> ok(data: T, message: String? = null): ResponseEntity<ApiResponse<T>> {
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = message,
                data = data
            )
        )
    }

    fun <T> error(
        message: String,
        status: HttpStatus = HttpStatus.BAD_REQUEST
    ): ResponseEntity<ApiResponse<T>> {
        return ResponseEntity.status(status).body(
            ApiResponse(
                success = false,
                message = message,
                data = null
            )
        )
    }
}