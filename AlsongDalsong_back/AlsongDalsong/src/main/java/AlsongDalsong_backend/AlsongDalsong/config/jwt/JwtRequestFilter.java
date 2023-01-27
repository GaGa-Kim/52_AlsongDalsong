package AlsongDalsong_backend.AlsongDalsong.config.jwt;

import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * jwt 유효성 체크 필터
 */
@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Value("${jwt.secret}")
    public String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 요청 헤더에서 토큰 값을 가져와서 유효성 검사
        final String jwtHeader = request.getHeader(AUTHORIZATION_HEADER);
        String username = null;
        String jwtToken = null;
        if (jwtHeader != null && jwtHeader.startsWith("Bearer ")) {
            jwtToken = jwtHeader.substring(7);
            try {
                // 토큰으로부터 얻은 인증 객체를 전역적으로 사용하도록 저장
                username = JWT.require(Algorithm.HMAC512(secret)).build().verify(jwtToken).getSubject();
                List<GrantedAuthority> roles = new ArrayList<>();
                String role = JWT.require(Algorithm.HMAC512(secret)).build().verify(jwtToken).getClaim("role").asString();
                roles.add(new SimpleGrantedAuthority(role));

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, roles);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (IllegalArgumentException e) {
                System.out.println("토큰을 얻을 수 없습니다.");
            } catch (TokenExpiredException e) {
                System.out.println("토큰이 만료되었습니다.");
            } catch (JWTVerificationException e) {
                System.out.println("유효하지 않은 토큰입니다.");
            }
        }

        filterChain.doFilter(request, response);
    }
}
