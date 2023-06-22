package one.terenin.security.verify;

import one.terenin.security.verify.impl.JwtVerificatorImpl;
import org.springframework.security.core.Authentication;

public interface JwtVerificator {

    JwtVerificatorImpl.VerificationResult verifyToken(String token);
    Authentication buildAuthenticationFromToken(String token);

}
