package com.earth.server.user.infra.security.jwt;

import com.earth.server.common.domain.ErrorCode;
import com.earth.server.common.infra.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class FilterExceptionHandler extends OncePerRequestFilter {
  private final ObjectMapper mapper;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
      setErrorResponse(HttpStatus.UNAUTHORIZED, response, ErrorCode.INVALID_TOKEN);
    } catch (ExpiredJwtException e) {
      setErrorResponse(HttpStatus.BAD_REQUEST, response, ErrorCode.EXPIRE_TOKEN);
    } catch (RuntimeException e) {
      setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, ErrorCode.UNEXPECTED_SERVER_ERROR);
    }
  }

  private void setErrorResponse(HttpStatus status, HttpServletResponse response, ErrorCode code) throws IOException {
    response.setStatus(status.value());
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(mapper.writeValueAsString(new ResponseBody(code)));
  }
}
