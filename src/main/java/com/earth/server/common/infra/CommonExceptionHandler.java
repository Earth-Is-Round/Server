package com.earth.server.common.infra;

import com.earth.server.common.domain.DomainException;
import com.earth.server.common.domain.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class CommonExceptionHandler {
    @ExceptionHandler(
            value = {
                    DomainException.class
            }
    )
    public ResponseEntity<?> badRequest(Exception e) {
        return JsonResponse.badRequest(ErrorCode.valueOf(e.getMessage()));
    }

    @ExceptionHandler(
            value = {
                    MethodArgumentNotValidException.class,
                    HttpMessageNotReadableException.class
            }
    )
    public ResponseEntity<?> invalidRequest(Exception e) {
        return JsonResponse.badRequest(ErrorCode.INVALID_REQUEST);
    }

    @ExceptionHandler({
            RuntimeException.class
    })
    public ResponseEntity<?> serverError() {
        return JsonResponse.serverError();
    }
}
