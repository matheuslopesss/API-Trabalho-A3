package com.bradesco.seguranca.apiconfirmacao.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configura as regras de segurança HTTP, exigindo autenticação Basic
     * para todas as requisições e desabilitando CSRF.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Configura as regras de requisição
                .authorizeHttpRequests((authorize) -> authorize
                        // Permite que qualquer caminho que comece com /api/v1/seguranca
                        // seja acessado SOMENTE se o usuário estiver autenticado (Basic Auth)
                        .requestMatchers(antMatcher("/api/v1/seguranca/**")).authenticated()

                        // Garante que todas as outras requisições (ex: favicon.ico, /error)
                        // sejam permitidas (sem autenticação)
                        .anyRequest().permitAll()
                )
                // 2. Define o tipo de autenticação como Basic (Username/Password)
                .httpBasic(withDefaults())
                // 3. Desabilita o CSRF para permitir POSTs simples de APIs
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    /**
     * Define o usuário e senha (admin/123456) para autenticação em memória.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("admin")
                // A senha é codificada usando o PasswordEncoder definido abaixo
                .password(passwordEncoder().encode("123456"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    /**
     * Define o codificador de senha (BCrypt) obrigatório no Spring Security 6+.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}