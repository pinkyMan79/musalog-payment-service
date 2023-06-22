package one.terenin.security.verify.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.terenin.exception.children.AuthenticationException;
import one.terenin.exception.children.ServiceCallException;
import one.terenin.exception.children.ServiceNotFoundException;
import one.terenin.exception.common.ErrorCode;
import one.terenin.security.details.user.UserDetailsImpl;
import one.terenin.security.details.user.UserDetailsServiceImpl;
import one.terenin.security.propertysource.JWTPropertySource;
import one.terenin.security.verify.JwtVerificator;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtVerificatorImpl implements JwtVerificator {

    private final JWTPropertySource propertySource;
    private final UserDetailsService userDetailsService;
    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;

    public record VerificationResult(Claims claims, String token) {}

    @Override
    public VerificationResult verifyToken(String token) {
        Claims claims;
        try {
            claims = extractClaimsFromToken(token);
        } catch (ExpiredJwtException e){
            claims = e.getClaims();
            log.info("{}{}{}{}",
                    token,
                    "\n Token expired, generate new. ",
                    "\n Expiration time: ",
                    new Date().getTime() - claims.getExpiration().getTime());
        }
        Date exp = claims.getExpiration();
        if (exp.before(new Date())) {
            String newToken = updateToken(token);
            if (newToken == null){
                throw new ServiceCallException(ErrorCode.SERVICE_CALL_REJECTED);
            }
            return new VerificationResult(claims, newToken);
        }
        return new VerificationResult(claims, token);
    }

    @Override
    public Authentication buildAuthenticationFromToken(String token) {
        VerificationResult verificationResult = verifyToken(token);
        Claims claims = verificationResult.claims;
        String authority = claims.get("authority", String.class);
        String username = claims.get("subject", String.class);
        GrantedAuthority role = new SimpleGrantedAuthority(authority);
        return new UsernamePasswordAuthenticationToken(userDetailsService
                .loadUserByUsername(username), role);
    }

    private Claims extractClaimsFromToken(String token) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(propertySource
                        .getJwtSecret()
                        .getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }

    private String updateToken(String expiredToken){
        // /api/v1/auth/token/update/{token}
        URI serviceURI = discoveryClient.getInstances("musalog-user-service").stream()
                .map(ServiceInstance::getUri)
                .findFirst()
                .map(si -> si.resolve("/api/v1/auth/token/update/" + expiredToken))
                .orElseThrow(() -> new ServiceNotFoundException(ErrorCode.SERVICE_NOT_FOUND));
        return restTemplate.getForEntity(serviceURI, String.class).getBody();
    }

}
