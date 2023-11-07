package com.finitx.karasignal.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * This class is responsible for handling forbidden exceptions.
 * @author Amin Norouzi
 */
@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
//        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
//        System.out.println("JwtAccessDeniedHandler: "+ exception.getMessage());
////        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
////        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//
//        OutputStream output = response.getOutputStream();
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(output, problem);
//
//        output.flush();

        handlerExceptionResolver.resolveException(request, response, null, exception);
    }
}