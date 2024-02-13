package AlsongDalsong_backend.AlsongDalsong.jwt;

import AlsongDalsong_backend.AlsongDalsong.config.GlobalConfig;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Token 생성 및 유효성 확인
 */
@Slf4j
@Component
public class TokenProvider {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int PREFIX_INDEX = 7;

    public String secret;
    public int expiration_time;

    public TokenProvider(GlobalConfig config) {
        this.secret = config.getJwt_secret();
        this.expiration_time = config.getJwt_expiration_time();
    }

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

    public String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(header) && header.startsWith(BEARER_PREFIX)) {
            return header.substring(PREFIX_INDEX);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secret)).build().verify(token);
            return true;
        } catch (IllegalArgumentException e) {
            log.error("토큰을 얻을 수 없습니다.");
            return false;
        } catch (TokenExpiredException e) {
            log.error("토큰이 만료되었습니다.");
            return false;
        } catch (JWTVerificationException e) {
            log.error("유효하지 않은 토큰입니다.");
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