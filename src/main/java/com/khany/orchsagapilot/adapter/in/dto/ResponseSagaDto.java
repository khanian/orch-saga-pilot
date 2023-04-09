package com.khany.orchsagapilot.adapter.in.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ResponseSagaDto(
    LocalDateTime eventTimestamp,
    Long orderId,
    Long customerId,
    String currentState,
    Object value){
}
