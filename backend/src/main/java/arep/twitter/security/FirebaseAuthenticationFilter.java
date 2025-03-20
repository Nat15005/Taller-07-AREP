package arep.twitter.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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

        // Si el path es público, no aplicar el filtro
        if (PUBLIC_PATHS.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Verificar el token JWT para paths no públicos
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Elimina "Bearer " del token

            try {
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
                request.setAttribute("userId", decodedToken.getUid()); // Guarda el ID del usuario en la solicitud
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
