package com.khany.orchsagapilot.adapter.in.dto;

import java.time.LocalDateTime;

public record requestSagaDto (
    LocalDateTime eventTimestamp,
    Long orderId,
    Long customerId,
    String currentState,
    Object value){
}
