package com.finitx.karasignal.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for doing some common operations for JWT authentication.
 *
 * @author Amin Norouzi
 */
@Component
public class JwtUtil {

    public static final String AUTH_HEADER_TYPE = "Bearer ";

    /**
     * Checks if the given authorization header is valid or not.
     *
     * @param header
     * @return boolean
     */
    public boolean isValidHeader(String header) {
        return StringUtils.isEmpty(header) || !StringUtils.startsWith(header, AUTH_HEADER_TYPE);
    }

    /**
     * Extracts the token from the given authorization header.
     *
     * @param header
     * @return String
     */
    public String extractHeader(String header) {
        return header.substring(AUTH_HEADER_TYPE.length());
    }
}
