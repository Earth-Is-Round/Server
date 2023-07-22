package com.earth.server.core.controller;

import com.earth.server.common.infra.JsonResponse;
import com.earth.server.core.dto.StepRequest;
import com.earth.server.core.service.CoreService;
import com.earth.server.user.domain.Auth;
import com.earth.server.user.domain.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CoreController {
    private final CoreService coreService;

    @PostMapping("/apis/steps")
    public ResponseEntity<?> recordSteps(@Valid @RequestBody StepRequest request, @Auth UserId userId) {
        return JsonResponse.ok(coreService.recordSteps(userId, request));
    }
}
