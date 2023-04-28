package com.earth.server.common.infra;

import com.earth.server.common.domain.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.earth.server.common.domain.ErrorCode.UNEXPECTED_SERVER_ERROR;

/*
 - 성공에 관해서는 200, 204만 사용
 - 클라이언트 로직 편의를 위해 body 는 response, errorCode 모두 null 이어도 우선 내려줌
 - 200 -> data 를 포함한 성공
 - 201 -> created
*/
public class JsonResponse {
  public static ResponseEntity<?> ok(Object data) {
    return ResponseEntity.ok(new ResponseBody(data, null));
  }

  public static ResponseEntity<?> created() {
    return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseBody(null, null));
  }

  public static ResponseEntity<?> serverError() {
    return ResponseEntity.internalServerError().body(new ResponseBody(null, UNEXPECTED_SERVER_ERROR));
  }

  public static ResponseEntity<?> badRequest(ErrorCode code) {
    return ResponseEntity.badRequest().body(new ResponseBody(null, code));
  }
}
