package com.example.kboard.filters

import com.example.kboard.jwt.JwtProvider
import com.example.kboard.repositories.UsersRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtProvider: JwtProvider,
    private val usersRepository: UsersRepository
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 1. Authorization 헤더에서 Bearer 토큰 추출
        val authHeader = request.getHeader("Authorization")
            ?.takeIf { it.startsWith("Bearer ") }
            ?.removePrefix("Bearer ")
            ?.trim()

        // 2. 토큰이 없거나 유효하지 않으면 필터 체인 계속 진행
        val userId = authHeader?.let { token -> jwtProvider.validateToken(token) }
        if (userId == null) {
            filterChain.doFilter(request, response)
            return
        }

        // 3. 토큰이 유효하면 DB에서 사용자 조회
        val user = usersRepository.findById(userId).orElse(null)
        if (user == null) {
            filterChain.doFilter(request, response)
            return
        }

        // 4. 인증 정보(SecurityContext)에 사용자 등록
        val auth = UsernamePasswordAuthenticationToken(user, null, emptyList())
        auth.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = auth

        // 5. 다음 필터로 요청 전달
        filterChain.doFilter(request, response)
    }
}