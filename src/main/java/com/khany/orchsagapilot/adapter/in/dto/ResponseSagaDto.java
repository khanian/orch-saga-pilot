package com.khany.orchsagapilot.adapter.in.dto;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public record ResponseSagaDto(
    Timestamp eventTimestamp,
    Long orderId,
    Long customerId,
    String currentState,
    Object value){
}
