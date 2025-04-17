package com.nuka.nuka_pos.api.authentication.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        // Set the response status to Unauthorized (401)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Set the error message in the response body
        response.getWriter().write("Error: Unauthorized access - Invalid or missing token.");
    }
}
