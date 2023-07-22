package com.earth.server.core.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;

import java.util.List;

public record StepRequest(@NotNull List<Step> steps){}
