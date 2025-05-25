package com.example.kboard.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/users"
                    ).permitAll() // 🔥 Swagger 경로는 인증 없이 허용
                    .anyRequest().authenticated()
            }
            .httpBasic(Customizer.withDefaults()) // 개발용 간단 인증 (필요 시 제거 가능)
            .csrf { it.disable() } // 테스트용으로 CSRF 끄기


        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return Argon2PasswordEncoder(
            16,    // saltLength (default: 16)
            32,    // hashLength (default: 32)
            1,     // parallelism (default: 1)
            65536, // memoryCost in KB (default: 65536 = 64MB)
            3      // iterations (default: 3)
        )
    }
}
