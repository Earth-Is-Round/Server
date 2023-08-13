package com.earth.server.core.dto;

import com.earth.server.core.repository.ItemEntity;
import com.earth.server.core.repository.SnowmanEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.List;

public record Snowman(
        Integer headSize,
        Integer bodySize,
        @JsonSerialize(using = LocalDateSerializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate startDate,
        @JsonSerialize(using = LocalDateSerializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate endDate,
        List<String> usedItems) {

    public Snowman(SnowmanEntity entity, List<ItemEntity> usedItems) {
        this(entity.getHeadSize(),
                entity.getBodySize(),
                entity.getStartDate(),
                entity.getEndDate(),
                usedItems.stream()
                        .map(ItemEntity::getName)
                        .toList());
    }
}
