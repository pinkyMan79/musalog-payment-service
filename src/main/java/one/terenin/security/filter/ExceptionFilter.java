package one.terenin.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

@Slf4j
@Component
public class ExceptionFilter implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        log.error(authException.getMessage());

        Map<String, Object> resp = new Hashtable<>();
        resp.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        resp.put("error", "Unauthorized");
        resp.put("msg", authException.getMessage());
        resp.put("path", request.getServletPath());
        log.info("{}{}", "generate response with info, see it for more info",
                authException.getMessage());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), resp);

    }

}
