package moyeothon.Team11_TwoAmpersandStar.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import moyeothon.Team11_TwoAmpersandStar.global.CustomAuthenticationFailureHandler;
import moyeothon.Team11_TwoAmpersandStar.global.jwt.exception.CustomAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    public JwtAuthorizationFilter(TokenProvider tokenProvider,
        CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
        this.tokenProvider = tokenProvider;
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {
        try {
            String token = resolveToken(request);
            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (CustomAuthenticationException e) {
            customAuthenticationFailureHandler.commence(request, response,
                new CustomAuthenticationException(e.getMessage()));
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}