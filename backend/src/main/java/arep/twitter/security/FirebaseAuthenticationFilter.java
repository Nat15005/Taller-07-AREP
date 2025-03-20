package arep.twitter.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

    private static final List<String> PUBLIC_PATHS = List.of(
            "/",
            "/index.html",
            "/home.html",
            "/styles.css",
            "/script.js",
            "/auth.js",
            "/login.css",
            "/favicon.ico",
            "/public/**",
            "/users",
            "/stream/posts"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Si el path es público, continuar sin filtrar
        if (PUBLIC_PATHS.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Obtener token del encabezado Authorization
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Elimina "Bearer " del token

            try {
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
                String email = decodedToken.getEmail(); // Obtener el email del usuario

                if (email != null) {
                    // Guardar email en el request
                    request.setAttribute("userEmail", email);

                    // Configurar autenticación en Spring Security
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(email, null,
                                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace(); // Log del error para depuración
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Depuración
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Usuario autenticado: " + SecurityContextHolder.getContext().getAuthentication());

        filterChain.doFilter(request, response);
    }
}
