package com.earth.server.core.dto;

import java.util.List;

public record SnowmanRecordListResponse(
        long total,
        List<Snowman> snowmen
){}
