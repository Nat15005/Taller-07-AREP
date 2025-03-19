package arep.twitter.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import arep.twitter.config.CorsConfig; // Importamos la config de CORS

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    private final FirebaseAuthenticationFilter firebaseAuthenticationFilter;
    private final CorsConfig corsConfig;

    public SecurityConfig(FirebaseAuthenticationFilter firebaseAuthenticationFilter, CorsConfig corsConfig) {
        this.firebaseAuthenticationFilter = firebaseAuthenticationFilter;
        this.corsConfig = corsConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(firebaseAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/", "/public", "/home.html", "/styles.css","/script.js", "/index.html", "/auth.js","/login.css", "/favicon.ico").permitAll() // Endpoints públicos
                        .anyRequest().permitAll() // Todos los demás requieren autenticación
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        })
                );

        return http.build();
    }
}
