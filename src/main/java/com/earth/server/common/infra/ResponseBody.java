package com.earth.server.common.infra;

import com.earth.server.common.domain.ErrorCode;

public record ResponseBody(Object response, ErrorCode errorCode) {
}
