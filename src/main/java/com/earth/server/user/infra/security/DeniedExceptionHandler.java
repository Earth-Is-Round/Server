package com.earth.server.user.infra.security;

import com.earth.server.common.infra.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import static com.earth.server.common.domain.ErrorCode.ACCESS_DENY;

@RequiredArgsConstructor
class DeniedExceptionHandler implements AccessDeniedHandler {
  private final ObjectMapper mapper;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.getWriter().write(mapper.writeValueAsString(new ResponseBody(null, ACCESS_DENY)));
  }
}
