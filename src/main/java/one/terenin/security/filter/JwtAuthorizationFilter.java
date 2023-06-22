package one.terenin.security.filter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import one.terenin.exception.children.TokenException;
import one.terenin.exception.common.ErrorCode;
import one.terenin.security.util.ExtractorHeaderUtil;
import one.terenin.security.verify.JwtVerificator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final ExtractorHeaderUtil extractor;
    private final JwtVerificator verificator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (extractor.isHeaderPresent(request)){
            try {
                String token = extractor.extractToken(request)
                        .orElseThrow(() -> new TokenException(ErrorCode.TOKEN_EXTRACTION_FAILED));
                Authentication authentication = verificator.buildAuthenticationFromToken(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            }catch (JwtException e){
                logger.info(e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }else {
            filterChain.doFilter(request, response);
        }
    }
}
