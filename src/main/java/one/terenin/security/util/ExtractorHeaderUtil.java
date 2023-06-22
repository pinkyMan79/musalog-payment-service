package one.terenin.security.util;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface ExtractorHeaderUtil {

    boolean isHeaderPresent(HttpServletRequest request);
    Optional<String> extractToken(HttpServletRequest request);

}
