package arep.twitter.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import arep.twitter.config.CorsConfig;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    private final FirebaseAuthenticationFilter firebaseAuthenticationFilter;

    public SecurityConfig(FirebaseAuthenticationFilter firebaseAuthenticationFilter) {
        this.firebaseAuthenticationFilter = firebaseAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF (necesario para H2 y otros casos)
                .addFilterBefore(firebaseAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Agrega tu filtro de autenticación
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index.html", "/home.html", "/login.css", "/styles.css", "/auth.js", "/script.js").permitAll() // Archivos estáticos
                        .requestMatchers("/public/**", "/users", "/stream/posts", "/posts").permitAll() // Endpoints públicos
                        .requestMatchers("/h2-console/**").permitAll() // Permite acceso a la consola H2
                        .anyRequest().authenticated() // Todos los demás requieren autenticación
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()) // Deshabilita frameOptions para H2
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        })
                );

        return http.build();
    }
}