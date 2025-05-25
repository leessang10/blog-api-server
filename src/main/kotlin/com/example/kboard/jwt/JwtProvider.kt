package com.example.kboard.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtProvider(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.expiration}") private val expiration: Long
) {
    private val algorithm = Algorithm.HMAC256(secret)

    fun createToken(userId: Long): String {
        val now = Date()
        val expiry = Date(now.time + expiration)
        return JWT.create()
            .withSubject(userId.toString()) // 사용자 식별자로 ID 사용
            .withIssuedAt(now)
            .withExpiresAt(expiry)
            .sign(algorithm)
    }

    fun validateToken(token: String): Long? {
        return try {
            val decodedJWT = JWT.require(algorithm)
                .build()
                .verify(token)

            decodedJWT.subject.toLong()
        } catch (e: Exception) {
            null // 🔐 유효하지 않은 토큰
        }
    }
}