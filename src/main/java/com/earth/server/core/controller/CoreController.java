package com.earth.server.core.controller;

import com.earth.server.common.infra.JsonResponse;
import com.earth.server.core.dto.CalculateRequest;
import com.earth.server.core.dto.StepRequest;
import com.earth.server.core.service.CoreService;
import com.earth.server.user.domain.Auth;
import com.earth.server.user.domain.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class CoreController {
    private final CoreService coreService;

    @PostMapping("/apis/steps")
    public ResponseEntity<?> recordSteps(@Valid @RequestBody StepRequest request, @Auth UserId userId) {
        return JsonResponse.ok(coreService.recordSteps(userId, request));
    }

    @PostMapping("/apis/snowmen")
    public ResponseEntity<?> calculateSnowmen(@Valid @RequestBody CalculateRequest request, @Auth UserId userId) {
        coreService.calculateSnowmen(userId, request);
        return JsonResponse.okWithNoData();
    }

    @GetMapping("/apis/records")
    public ResponseEntity<?> getRecord(
            @RequestParam("startDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate startDate,
            @Auth UserId userId
    ) {
        return JsonResponse.ok(coreService.getRecord(userId, startDate));
    }

    @GetMapping("/apis/records/list")
    public ResponseEntity<?> getRecords(
            @RequestParam("maxDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate maxDate,
            @Auth UserId userId
    ) {
        return JsonResponse.ok(coreService.getRecords(userId, maxDate));
    }
}
