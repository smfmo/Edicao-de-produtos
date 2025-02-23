package com.samuel.demo.configuration;

import com.samuel.demo.repository.UserAdmRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/loginAdm").permitAll() // Permite acesso público ao login
                        .requestMatchers("/admin/**", "/addProduto", "/editar/**", "/deletarProduto/**", "/restaurarProduto/**", "/controleVenda").hasRole("ADMIN") // Restringe acesso a URLs de admin
                        .anyRequest().permitAll() // Permite acesso a todas as outras URLs
                )
                .formLogin(form -> form
                        .loginPage("/loginAdm") // Página de login personalizada
                        .defaultSuccessUrl("/admin", true) // Redireciona para /admin após o login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/loginAdm?logout") // Redireciona para /loginAdm após o logout
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/loginAdm?error") // Redireciona para /loginAdm em caso de acesso negado
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserAdmRepository usuarioRepository) {
        return username -> usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Usado para criptografar senhas
    }
}