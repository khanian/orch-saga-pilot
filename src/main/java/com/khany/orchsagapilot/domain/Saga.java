package com.khany.orchsagapilot.domain;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Saga(
        LocalDateTime eventTimestamp,
        Long orderId,
        Long customerId,
        String currentState,
        String value) {}
