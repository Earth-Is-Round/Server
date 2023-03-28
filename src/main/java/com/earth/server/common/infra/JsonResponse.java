package com.earth.server.common.infra;

import com.earth.server.common.domain.ErrorCode;
import org.springframework.http.ResponseEntity;

import static com.earth.server.common.domain.ErrorCode.UNEXPECTED_SERVER_ERROR;

/*
 - 성공에 관해서는 200, 204만 사용
 - 200 -> data 를 포함한 성공
 - 200 -> data 는 없는 성공, no content
*/
public class JsonResponse {
  public static ResponseEntity<?> ok(Object data) {
    return ResponseEntity.ok(new ResponseBody(data));
  }

  public static ResponseEntity<?> noContent() {
    return ResponseEntity.noContent().build();
  }

  public static ResponseEntity<?> serverError() {
    return ResponseEntity.internalServerError().body(new ResponseBody(UNEXPECTED_SERVER_ERROR));
  }

  public static ResponseEntity<?> badRequest(ErrorCode code) {
    return ResponseEntity.badRequest().body(new ResponseBody(code));
  }
}
