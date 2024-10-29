package moyeothon.Team11_TwoAmpersandStar.global.jwt.exception;

import org.springframework.security.core.AuthenticationException;
public class CustomAuthenticationException extends AuthenticationException {
    public CustomAuthenticationException(String message) {
        super(message);
    }
}
