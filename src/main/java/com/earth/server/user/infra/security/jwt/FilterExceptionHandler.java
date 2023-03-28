package com.earth.server.user.infra.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class FilterExceptionHandler extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      setErrorResponse(HttpStatus.UNAUTHORIZED, response, "잘못된 JWT 서명입니다.");
    } catch (ExpiredJwtException e) {
      setErrorResponse(HttpStatus.BAD_REQUEST, response, "만료된 JWT 토큰입니다.");
    } catch (UnsupportedJwtException e) {
      setErrorResponse(HttpStatus.UNAUTHORIZED, response, "지원되지 않는 JWT 토큰입니다.");
    } catch (IllegalArgumentException e) {
      setErrorResponse(HttpStatus.UNAUTHORIZED, response, "JWT 토큰이 잘못되었습니다.");
    } catch (RuntimeException e) {
      setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, "예상치 못한 서버 오류입니다.");
    }
  }

  private void setErrorResponse(HttpStatus status, HttpServletResponse response, String message) throws IOException {
    response.setStatus(status.value());
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(message);
  }
}
