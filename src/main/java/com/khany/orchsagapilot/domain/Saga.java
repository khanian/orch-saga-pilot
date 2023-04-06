package com.khany.orchsagapilot.domain;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public record Saga(
        Timestamp eventTimestamp,
        Long orderId,
        Long customerId,
        String currentState,
        String value) {
}
