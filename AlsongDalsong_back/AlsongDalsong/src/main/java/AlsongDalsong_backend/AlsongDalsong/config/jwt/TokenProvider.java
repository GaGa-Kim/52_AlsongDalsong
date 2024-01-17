package AlsongDalsong_backend.AlsongDalsong.config.jwt;

import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TokenProvider {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret}")
    public String secret;

    @Value("${jwt.expiration_time}")
    public int expiration_time;

    public String createToken(User user) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration_time))
                .withClaim("role", user.getRole().getRole())
                .sign(Algorithm.HMAC512(secret));
    }

    public Authentication getAuthentication(String token) {
        return new UsernamePasswordAuthenticationToken(getUserEmail(token), token, getUserRole(token));
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(header) && header.startsWith(BEARER_PREFIX)) {
            return header.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token, HttpServletRequest request) {
        try {
            JWT.require(Algorithm.HMAC512(secret)).build().verify(token);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("토큰을 얻을 수 없습니다.");
            return false;
        } catch (TokenExpiredException e) {
            System.out.println("토큰이 만료되었습니다.");
            return false;
        } catch (JWTVerificationException e) {
            System.out.println("유효하지 않은 토큰입니다.");
            return false;
        }
    }

    private String getUserEmail(String token) {
        return JWT.require(Algorithm.HMAC512(secret)).build().verify(token).getSubject();
    }

    private List<GrantedAuthority> getUserRole(String token) {
        Claim claim = JWT.require(Algorithm.HMAC512(secret)).build().verify(token).getClaim("role");
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(claim.asString()));
        return roles;
    }
}