package AlsongDalsong_backend.AlsongDalsong.config.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * jwt 유효성 체크 필터
 */
@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getServletPath();
        if (path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
        } else {
            String token = tokenProvider.resolveAccessToken(request);
            boolean isTokenValid = tokenProvider.validateToken(token, request);
            if (StringUtils.hasText(token) && isTokenValid) {
                this.setAuthentication(token);
            }
            filterChain.doFilter(request, response);
        }
    }

    private void setAuthentication(String token) {
        Authentication authentication = tokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
